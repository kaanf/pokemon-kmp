import SwiftUI

struct ToastBanner: View {
    let message: String
    var iconName: String? = nil
    var actionTitle: LocalizedStringKey? = nil
    var onAction: (() -> Void)? = nil

    var body: some View {
        HStack(spacing: AppTheme.Spacing.md) {
            if let iconName {
                Image(iconName)
                    .renderingMode(.template)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 22, height: 22)
                    .foregroundStyle(AppTheme.Colors.pokeball)
                    .frame(width: 40, height: 40)
                    .background(AppTheme.Colors.pokeball.opacity(0.12), in: Circle())
            }

            Text(message)
                .appFont(AppTheme.Typography.titleMedium)
                .foregroundStyle(AppTheme.Colors.contentPrimary)
                .frame(maxWidth: .infinity, alignment: .leading)

            if let actionTitle, let onAction {
                Button(action: onAction) {
                    Text(actionTitle)
                        .appFont(AppTheme.Typography.titleMedium)
                        .foregroundStyle(AppTheme.Colors.pokeball)
                        .padding(.horizontal, AppTheme.Spacing.sm)
                        .padding(.vertical, AppTheme.Spacing.xs)
                }
                .buttonStyle(.plain)
            }
        }
        .padding(AppTheme.Spacing.md)
        .background(AppTheme.Colors.surface, in: RoundedRectangle(cornerRadius: AppTheme.Radius.card))
        .overlay(
            RoundedRectangle(cornerRadius: AppTheme.Radius.card)
                .stroke(AppTheme.Colors.surfaceStroke, lineWidth: 1)
        )
        .shadow(color: .black.opacity(0.12), radius: 8, y: 2)
        .padding(.horizontal, AppTheme.Spacing.xl)
    }
}
