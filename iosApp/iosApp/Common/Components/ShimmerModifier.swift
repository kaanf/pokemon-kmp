import SwiftUI

struct ShimmerModifier: ViewModifier {

    @State private var phase: CGFloat = 0

    func body(content: Content) -> some View {
        content
            .overlay {
                GeometryReader { geometry in
                    let width = geometry.size.width
                    let height = geometry.size.height
                    LinearGradient(
                        stops: [
                            .init(color: AppTheme.Colors.shimmerBase, location: 0.35),
                            .init(color: AppTheme.Colors.shimmerHighlight, location: 0.5),
                            .init(color: AppTheme.Colors.shimmerBase, location: 0.65)
                        ],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    )
                    .frame(width: width * 3, height: height)
                    .offset(x: -width * 2 + phase * width * 2)
                }
            }
            .mask(content)
            .onAppear {
                withAnimation(.linear(duration: 1.2).repeatForever(autoreverses: false)) {
                    phase = 1
                }
            }
    }
}

extension View {
    func shimmer() -> some View {
        modifier(ShimmerModifier())
    }
}
