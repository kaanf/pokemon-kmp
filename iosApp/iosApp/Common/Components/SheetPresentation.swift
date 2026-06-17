import SwiftUI

// MARK: - Adaptive modal (bottom sheet in portrait, dialog in landscape)
extension View {
    func adaptiveModal<SheetBody: View, DialogBody: View>(
        isPresented: Binding<Bool>,
        @ViewBuilder sheet: @escaping () -> SheetBody,
        @ViewBuilder dialog: @escaping () -> DialogBody
    ) -> some View {
        modifier(AdaptiveModal(isPresented: isPresented, sheet: sheet, dialog: dialog))
    }
}

private struct AdaptiveModal<SheetBody: View, DialogBody: View>: ViewModifier {
    @Binding var isPresented: Bool
    @ViewBuilder let sheet: () -> SheetBody
    @ViewBuilder let dialog: () -> DialogBody

    @Environment(\.verticalSizeClass) private var verticalSizeClass
    @State private var sheetHeight: CGFloat = 0

    func body(content: Content) -> some View {
        let isCompact = verticalSizeClass.isCompact
        
        content
            .background { sheetHeightProbe }
            .background {
                Color.clear
                    .sheet(isPresented: branch(whenActive: !isCompact)) {
                        sheet()
                            .presentationDetents([.height(sheetHeight)])
                            .id(sheetHeight)
                    }
            }
            .background {
                Color.clear
                    .fullScreenCover(isPresented: branch(whenActive: isCompact)) {
                        dialog().presentationBackground(.clear)
                    }
            }
    }

    private var sheetHeightProbe: some View {
        sheet()
            .background {
                GeometryReader { proxy in
                    Color.clear.task(id: proxy.size.height) {
                        sheetHeight = proxy.size.height + proxy.safeAreaInsets.bottom
                    }
                }
            }
            .hidden()
            .allowsHitTesting(false)
            .accessibilityHidden(true)
    }

    private func branch(whenActive active: Bool) -> Binding<Bool> {
        Binding(
            get: { isPresented && active },
            set: { if !$0 { isPresented = false } }
        )
    }
}

// MARK: - Sheet grabber
struct SheetGrabber: View {
    var body: some View {
        Capsule()
            .fill(AppTheme.Colors.contentSecondary.opacity(0.5))
            .frame(width: 36, height: 5)
            .padding(.top, AppTheme.Spacing.sm)
            .padding(.bottom, AppTheme.Spacing.xs)
    }
}
