import SwiftUI

struct SettingsRow<Trailing: View>: View {

    let title: LocalizedStringKey
    let imageName: String
    @ViewBuilder let trailing: () -> Trailing

    @Environment(\.verticalSizeClass) private var verticalSizeClass

    var body: some View {
        HStack(spacing: AppTheme.Spacing.md) {
            Image(imageName)
                .renderingMode(.template)
                .resizable()
                .scaledToFit()
                .foregroundStyle(AppTheme.Colors.contentPrimary)
                .frame(width: 24, height: 24)

            Text(title)
                .appFont(AppTheme.Typography.titleLarge)
                .foregroundStyle(AppTheme.Colors.contentPrimary)

            Spacer(minLength: AppTheme.Spacing.sm)

            trailing()
        }
        .frame(height: verticalSizeClass.isCompact ? 56 : 72)
        .padding(.horizontal, 20)
        .contentShape(Rectangle())
    }
}

struct SettingsDivider: View {
    var body: some View {
        Divider().overlay(AppTheme.Colors.surfaceStroke)
    }
}
