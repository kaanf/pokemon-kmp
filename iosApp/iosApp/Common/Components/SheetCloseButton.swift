import SwiftUI

struct SheetCloseButton: View {

    let onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            Image("ic_close")
                .renderingMode(.template)
                .resizable()
                .scaledToFit()
                .frame(width: 20, height: 20)
                .foregroundStyle(AppTheme.Colors.contentPrimary)
                .padding(6)
                .background(AppTheme.Colors.navigationPrimary, in: Circle())
        }
        .buttonStyle(.plain)
        .accessibilityLabel(Text("common.close"))
    }
}
