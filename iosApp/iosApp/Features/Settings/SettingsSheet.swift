import KMPObservableViewModelSwiftUI
import SharedLogic
import SwiftUI

extension View {
    func settingsModal(isPresented: Binding<Bool>) -> some View {
        modifier(SettingsModal(isPresented: isPresented))
    }
}

private struct SettingsModal: ViewModifier {

    @Binding var isPresented: Bool

    @StateViewModel private var viewModel = SharedDI.settingsViewModel()

    func body(content: Content) -> some View {
        content.adaptiveModal(
            isPresented: $isPresented,
            sheet: {
                SettingsSheet(
                    state: viewModel.state,
                    onAction: { viewModel.onAction(action: $0) },
                    onDismiss: { isPresented = false }
                )
            },
            dialog: {
                SettingsDialog(
                    state: viewModel.state,
                    onAction: { viewModel.onAction(action: $0) },
                    onDismiss: { isPresented = false }
                )
            }
        )
    }
}

struct SettingsSheet: View {

    let state: SettingsState
    let onAction: (SettingsAction) -> Void
    let onDismiss: () -> Void

    var body: some View {
        VStack(spacing: 0) {
            SettingsContent(
                state: state,
                onAction: onAction,
                onClose: onDismiss
            )
        }
        .frame(maxWidth: .infinity)
        .background(AppTheme.Colors.background)
        .presentationDragIndicator(.visible)
        .presentationCornerRadius(32)
        .presentationBackground(AppTheme.Colors.background)
    }
}

struct SettingsDialog: View {

    let state: SettingsState
    let onAction: (SettingsAction) -> Void
    let onDismiss: () -> Void

    var body: some View {
        AppDialog(onDismiss: onDismiss) { dismiss in
            SettingsContent(
                state: state,
                onAction: onAction,
                onClose: dismiss
            )
        }
    }
}

struct SettingsContent: View {

    let state: SettingsState
    let onAction: (SettingsAction) -> Void
    let onClose: () -> Void

    @Environment(\.verticalSizeClass) private var verticalSizeClass

    var body: some View {
        let settings = state.settings

        VStack(alignment: .leading, spacing: AppTheme.Spacing.md) {
            ZStack {
                Text("settings.title")
                    .appFont(AppTheme.Typography.titleLarge)
                    .foregroundStyle(AppTheme.Colors.contentPrimary)
                    .padding(.vertical, AppTheme.Spacing.md)

                HStack {
                    SheetCloseButton(onTap: onClose)
                    Spacer(minLength: 0)
                }
            }
            .frame(maxWidth: .infinity)

            VStack(spacing: 0) {
                SettingsRow(title: "settings.unit", imageName: "ic_ruler") {
                    UnitToggle(
                        selected: settings.unitSystem,
                        onSelect: { onAction(SettingsActionOnUnitSystemSelect(unitSystem: $0)) }
                    )
                }

                SettingsDivider()

                SettingsRow(title: "settings.time_format", imageName: "ic_time") {
                    Toggle("", isOn: twentyFourHourBinding(settings: settings))
                        .labelsHidden()
                        .tint(Color(hex: 0x34C759))
                }

                SettingsDivider()

                ModeDropdown(
                    selected: settings.themeMode,
                    onSelect: { onAction(SettingsActionOnThemeModeSelect(themeMode: $0)) }
                )

                SettingsDivider()

                SettingsAboutRow()
            }
            .background(AppTheme.Colors.surface, in: RoundedCornerShape(cornerRadius: 24))
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.horizontal, AppTheme.Spacing.lg)
        .padding(.top, AppTheme.Spacing.md)
        .padding(.bottom, verticalSizeClass.isCompact ? AppTheme.Spacing.lg : AppTheme.Spacing.xl)
    }

    private func twentyFourHourBinding(settings: AppSettings) -> Binding<Bool> {
        Binding(
            get: { settings.timeFormat == .twentyFourHour },
            set: { checked in
                onAction(
                    SettingsActionOnTimeFormatSelect(
                        timeFormat: checked ? .twentyFourHour : .twelveHour
                    )
                )
            }
        )
    }
}
