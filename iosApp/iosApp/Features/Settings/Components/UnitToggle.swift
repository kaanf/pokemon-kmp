import SharedLogic
import SwiftUI

struct UnitToggle: View {

    let selected: UnitSystem
    let onSelect: (UnitSystem) -> Void

    private let options: [(unit: UnitSystem, label: LocalizedStringKey)] = [
        (.metric, "settings.unit.metric"),
        (.imperial, "settings.unit.imperial"),
    ]

    var body: some View {
        let selectedIndex = options.firstIndex(where: { $0.unit == selected }) ?? 0

        GeometryReader { proxy in
            let itemWidth = proxy.size.width / CGFloat(options.count)

            ZStack(alignment: .leading) {
                Capsule()
                    .fill(AppTheme.Colors.surface)
                    .overlay(Capsule().stroke(AppTheme.Colors.surfaceStroke, lineWidth: 1))
                    .frame(width: itemWidth)
                    .offset(x: itemWidth * CGFloat(selectedIndex))

                HStack(spacing: 0) {
                    ForEach(Array(options.enumerated()), id: \.offset) { index, option in
                        Button(action: { onSelect(option.unit) }) {
                            Text(option.label)
                                .appFont(AppTheme.Typography.titleMedium)
                                .foregroundStyle(
                                    index == selectedIndex
                                        ? AppTheme.Colors.contentPrimary
                                        : AppTheme.Colors.contentSecondary
                                )
                                .frame(width: itemWidth, height: proxy.size.height)
                        }
                        .buttonStyle(.plain)
                    }
                }
            }
            .animation(.easeInOut(duration: 0.25), value: selectedIndex)
        }
        .frame(width: 180, height: 32)
        .padding(3)
        .background(AppTheme.Colors.background, in: Capsule())
    }
}
