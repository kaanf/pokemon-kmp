import SwiftUI

struct AppDialog<Content: View>: View {

    let onDismiss: () -> Void
    @ViewBuilder let content: (_ dismiss: @escaping () -> Void) -> Content

    @State private var appeared = false

    var body: some View {
        ZStack {
            Color.black.opacity(appeared ? 0.4 : 0)
                .ignoresSafeArea()
                .onTapGesture(perform: dismiss)

            content(dismiss)
                .frame(maxWidth: 400)
                .background(AppTheme.Colors.background, in: RoundedCornerShape(cornerRadius: 24))
                .padding(.horizontal, AppTheme.Spacing.xl)
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
