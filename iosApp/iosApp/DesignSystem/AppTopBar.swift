import SharedLogic
import SwiftUI

struct AppTopBar: View {

    let state: AppTopBarState
    var onBack: () -> Void = {}
    var onSettings: () -> Void = {}

    @Environment(\.verticalSizeClass) private var verticalSizeClass

    private var isCompact: Bool { verticalSizeClass.isCompact }

    var body: some View {
        VStack(spacing: 0) {
            HStack(spacing: 0) {
                leading
                Spacer(minLength: 0)
                SettingsIconButton(onTap: onSettings)
            }
            .padding(.horizontal, 20)
            .padding(.vertical, isCompact ? AppTheme.Spacing.sm : AppTheme.Spacing.lg)

            if !(state is AppTopBarStateDetail) {
                Divider().overlay(AppTheme.Colors.dividerMedium)
            }
        }
    }

    @ViewBuilder
    private var leading: some View {
        if state is AppTopBarStateDetail {
            backButton
        } else if let team = state as? AppTopBarStateTeam {
            title(Text("team.header_title \(Int(team.teamCount))"))
        } else {
            title(Text("pokedex.header_title"))
        }
    }

    private func title(_ text: Text) -> some View {
        text
            .appFont(isCompact ? AppTheme.Typography.headlineMedium : AppTheme.Typography.headlineLarge)
            .foregroundStyle(AppTheme.Colors.contentPrimary)
    }

    private var backButton: some View {
        Button(action: onBack) {
            Image("ic_left_chevron")
                .renderingMode(.template)
                .resizable()
                .scaledToFit()
                .frame(width: 18, height: 18)
                .foregroundStyle(AppTheme.Colors.contentPrimary)
                .padding(AppTheme.Spacing.sm)
                .background(AppTheme.Colors.surface, in: Circle())
        }
        .buttonStyle(.plain)
        .accessibilityLabel(Text("detail.back"))
    }
}
