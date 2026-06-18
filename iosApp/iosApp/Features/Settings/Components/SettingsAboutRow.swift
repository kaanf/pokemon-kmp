import SwiftUI

struct SettingsAboutRow: View {

    @State private var showAbout = false

    var body: some View {
        Button(action: { showAbout = true }) {
            SettingsRow(title: "settings.about", imageName: "ic_info") {
                Text("›")
                    .appFont(AppTheme.Typography.titleLarge)
                    .foregroundStyle(AppTheme.Colors.contentTertiary)
            }
        }
        .buttonStyle(.plain)
        .fullScreenCover(isPresented: $showAbout) {
            AboutDialog(onDismiss: { showAbout = false })
                .presentationBackground(.clear)
        }
    }
}

private struct AboutDialog: View {

    let onDismiss: () -> Void

    @State private var appeared = false

    var body: some View {
        ZStack {
            Color.black.opacity(appeared ? 0.4 : 0)
                .ignoresSafeArea()
                .onTapGesture(perform: dismiss)

            VStack(spacing: AppTheme.Spacing.md) {
                ZStack {
                    Text("settings.about")
                        .appFont(AppTheme.Typography.titleLarge)
                        .foregroundStyle(AppTheme.Colors.contentPrimary)

                    HStack {
                        SheetCloseButton(onTap: dismiss)
                        Spacer(minLength: 0)
                    }
                }
                .frame(maxWidth: .infinity)

                VStack(spacing: AppTheme.Spacing.lg) {
                    Image("ic_pokeball_dialog")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 72, height: 72)

                    Text("settings.about.message")
                        .appFont(AppTheme.Typography.titleMedium)
                        .foregroundStyle(AppTheme.Colors.contentTertiary)
                }
                .frame(maxWidth: .infinity)
                .padding(.bottom, AppTheme.Spacing.lg)
            }
            .padding(AppTheme.Spacing.lg)
            .background(AppTheme.Colors.background, in: RoundedCornerShape(cornerRadius: 24))
            .padding(.horizontal, 40)
            .scaleEffect(appeared ? 1 : 0.92)
            .opacity(appeared ? 1 : 0)
        }
        .onAppear {
            withAnimation(.spring(response: 0.3, dampingFraction: 0.85)) { appeared = true }
        }
    }

    private func dismiss() {
        withAnimation(.easeIn(duration: 0.15)) {
            appeared = false
        } completion: {
            onDismiss()
        }
    }
}
