import SwiftUI

struct TeamFullSheet: View {

    let onDismiss: () -> Void

    var body: some View {
        VStack(spacing: 0) {
            TeamFullContent(onClose: onDismiss)
        }
        .frame(maxWidth: .infinity)
        .presentationDragIndicator(.hidden)
        .presentationCornerRadius(24)
        .presentationBackground(AppTheme.Colors.background)
    }
}


struct TeamFullContent: View {

    var isCompact: Bool = false
    let onClose: () -> Void

    var body: some View {
        VStack(spacing: AppTheme.Spacing.sm) {
            Image("img_pokedex_box")
                .resizable()
                .scaledToFit()
                .frame(width: isCompact ? 96 : 256, height: isCompact ? 96 : 256)

            Text("pokedex.team_full.title")
                .appFont(isCompact ? AppTheme.Typography.headlineMedium : AppTheme.Typography.headlineLarge)
                .foregroundStyle(AppTheme.Colors.contentPrimary)
                .multilineTextAlignment(.center)

            Text("pokedex.team_full.message")
                .appFont(AppTheme.Typography.titleMedium)
                .foregroundStyle(AppTheme.Colors.onBackground)
                .multilineTextAlignment(.center)
                .lineSpacing(8)
                .padding(.vertical, AppTheme.Spacing.sm)
                .padding(.horizontal, AppTheme.Spacing.lg)

            Button(action: onClose) {
                Text("pokedex.team_full.dismiss")
                    .appFont(isCompact ? AppTheme.Typography.titleMedium : AppTheme.Typography.headlineMedium)
                    .foregroundStyle(AppTheme.Colors.buttonText)
                    .frame(maxWidth: isCompact ? 240 : .infinity)
                    .padding(.vertical, isCompact ? AppTheme.Spacing.md : AppTheme.Spacing.lg)
                    .background(
                        LinearGradient(
                            colors: [
                                AppTheme.Colors.buttonGradientStart,
                                AppTheme.Colors.buttonGradientEnd,
                            ],
                            startPoint: .top,
                            endPoint: .bottom
                        )
                    )
                    .clipShape(Capsule())
            }
            .buttonStyle(.plain)
            .padding(.horizontal, AppTheme.Spacing.lg)
            .padding(.top, isCompact ? AppTheme.Spacing.md : AppTheme.Spacing.xl)
        }
        .padding(AppTheme.Spacing.md)
        .frame(maxWidth: .infinity)
    }
}
