import SharedLogic
import SwiftUI

struct ErrorStateView: View {

    let error: DataErrorRemote
    let onRetry: () -> Void

    @Environment(\.verticalSizeClass) private var verticalSizeClass

    private var isOffline: Bool {
        switch error {
        case .noInternet: true
        default: false
        }
    }

    private var imageSize: CGFloat {
        verticalSizeClass.isCompact ? 78 : 100
    }

    var body: some View {
        GeometryReader { proxy in
            ScrollView {
                VStack(spacing: AppTheme.Spacing.sm) {
                    Image("img_sad_cubone")
                        .resizable()
                        .scaledToFit()
                        .frame(width: imageSize, height: imageSize)

                    Text(isOffline ? "error.no_internet_title" : "error.generic_title")
                        .appFont(AppTheme.Typography.headlineSmall)
                        .foregroundStyle(AppTheme.Colors.contentPrimary)
                        .multilineTextAlignment(.center)

                    Text(error.userMessage)
                        .appFont(AppTheme.Typography.bodyMedium)
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
                .frame(maxWidth: .infinity, minHeight: proxy.size.height)
            }
        }
    }
}
