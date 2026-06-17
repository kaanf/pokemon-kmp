import SwiftUI

struct RoundedTypeIcon: View {

    let imageName: String
    let tint: Color
    let background: Color

    var body: some View {
        Image(imageName)
            .renderingMode(.template)
            .resizable()
            .scaledToFit()
            .foregroundStyle(tint)
            .frame(width: 20, height: 20)
            .padding(AppTheme.Spacing.sm)
            .background(background, in: Circle())
    }
}
