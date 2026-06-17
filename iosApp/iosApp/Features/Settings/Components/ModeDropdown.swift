import SharedLogic
import SwiftUI

struct ModeDropdown: View {

    let selected: ThemeMode
    let onSelect: (ThemeMode) -> Void

    var body: some View {
        Menu {
            ForEach([ThemeMode.system, ThemeMode.light, ThemeMode.dark], id: \.ordinal) { mode in
                Button(action: { onSelect(mode) }) {
                    Text(label(for: mode))
                }
            }
        } label: {
            SettingsRow(title: "settings.mode", imageName: "ic_mode") {
                HStack(spacing: AppTheme.Spacing.sm) {
                    Text(label(for: selected))
                        .appFont(AppTheme.Typography.titleMedium)
                    Image(systemName: "chevron.down")
                        .font(.system(size: 12, weight: .semibold))
                }
                .foregroundStyle(AppTheme.Colors.contentTertiary)
            }
        }
        .buttonStyle(.plain)
    }

    private func label(for mode: ThemeMode) -> LocalizedStringKey {
        switch mode {
        case .light: "settings.mode.light"
        case .dark: "settings.mode.dark"
        default: "settings.mode.system"
        }
    }
}
