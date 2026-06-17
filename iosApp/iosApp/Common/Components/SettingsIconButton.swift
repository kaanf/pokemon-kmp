import SwiftUI

struct SettingsIconButton: View {

    let onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            Image("ic_settings")
                .renderingMode(.template)
                .resizable()
                .scaledToFit()
                .frame(width: 20, height: 20)
                .foregroundStyle(AppTheme.Colors.contentPrimary)
                .padding(AppTheme.Spacing.sm)
                .background(AppTheme.Colors.surface, in: Circle())
        }
        .buttonStyle(.plain)
        .accessibilityLabel(Text("settings.title"))
    }
}
