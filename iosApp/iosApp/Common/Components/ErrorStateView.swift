import SharedLogic
import SwiftUI

struct ErrorStateView: View {

    let error: DataErrorRemote
    let onRetry: () -> Void

    private var isOffline: Bool {
        switch error {
        case .noInternet: true
        default: false
        }
    }

    var body: some View {
        VStack(spacing: AppTheme.Spacing.sm) {
            Image("img_sad_cubone")
                .resizable()
                .scaledToFit()
                .frame(width: 88, height: 88)

            Text(isOffline ? "error.no_internet_title" : "error.generic_title")
                .appFont(AppTheme.Typography.headlineLarge)
                .foregroundStyle(AppTheme.Colors.contentPrimary)
                .multilineTextAlignment(.center)

            Text(error.userMessage)
                .appFont(AppTheme.Typography.titleMedium)
                .foregroundStyle(AppTheme.Colors.contentSecondary)
                .multilineTextAlignment(.center)
            
            Spacer()
                .frame(height: AppTheme.Spacing.md)

            Button(action: onRetry) {
                Text("error.retry")
                    .appFont(AppTheme.Typography.titleMedium)
                    .foregroundStyle(AppTheme.Colors.buttonText)
                    .padding(.horizontal, 28)
                    .padding(.vertical, 10)
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
            .padding(.top, AppTheme.Spacing.sm)
        }
        .padding(AppTheme.Spacing.xl)
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
