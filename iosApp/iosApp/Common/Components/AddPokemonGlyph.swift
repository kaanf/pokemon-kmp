import SwiftUI

struct AddPokemonGlyph: View {

    var bodyColor: Color = AppTheme.Colors.surfaceTertiary
    var plusColor: Color = AppTheme.Colors.contentSecondary

    private static let viewport: CGFloat = 176

    var body: some View {
        GeometryReader { proxy in
            let side = min(proxy.size.width, proxy.size.height)

            ZStack {
                VectorPathShape(pathData: Self.bodyPath, viewport: Self.viewport)
                    .fill(bodyColor, style: FillStyle(eoFill: true))

                VectorPathShape(pathData: Self.plusPath, viewport: Self.viewport)
                    .stroke(
                        plusColor,
                        style: StrokeStyle(
                            lineWidth: 5 * side / Self.viewport,
                            lineCap: .round,
                            lineJoin: .round
                        )
                    )
            }
        }
        .aspectRatio(1, contentMode: .fit)
    }

    private static let bodyPath =
        "M11.16,18.76C9.8,15.4 8.42,9.04 14.65,10.12C25.02,11.93 38.04,19.7 45.26,26.99C47.16,28.51 49.34,30.79 50.99,32.58C53.97,35.73 56.71,39.09 59.18,42.63C68.49,37.1 80.22,35.95 90.7,38.44C94.25,39.32 97.64,40.72 100.76,42.59C102.03,40.96 103.32,39.33 104.62,37.72L105.18,37.03C106.94,34.86 109.17,32.63 111.1,30.54C113.02,28.9 114.77,26.95 116.68,25.34C123.48,19.64 130.98,14.51 139.53,11.66C141.86,10.89 144.45,10.01 146.93,10.09C149.62,10.19 150.45,12.46 149.91,14.75C145.77,32.41 131.98,47.06 116.45,56.21C115.77,56.62 115.1,57.02 114.39,57.35C115.6,60.41 116.34,63.63 116.58,66.9C129.47,57.04 145.31,49.34 160.67,43.75C169.95,40.38 166.1,52.69 166.32,57.47C166.15,59.06 165.92,63.16 165.72,64.46C165.32,67.05 164.99,80.26 162.73,81.22C156.01,84.05 144.25,86.85 137.46,89.94L143.22,100.83C145.53,105.27 148.75,109.03 142.67,111.78C138.69,113.58 134.25,115.42 130.38,117.38C131.89,119.61 135.98,125.73 136.35,127.7C136.73,129.67 134.01,131.39 134.01,131.39C134.01,131.39 130.76,132.96 129.2,133.73C126.36,135.24 123.1,136.97 120.17,138.3C120.18,143.91 118.13,149.6 114.09,153.66C120.85,159.62 121.38,167.32 110.26,166.84C104.69,166.6 96.42,162.19 92.85,157.94C89.85,157.28 82.91,155.99 80.12,156.01C76.87,156.04 70.7,157.23 67.33,157.84C67.2,158 67.07,158.15 66.94,158.3C63.01,162.71 55.45,166.36 49.46,166.89C46.22,167.17 41.04,166.02 41.1,161.95C41.14,158.57 43.57,155.93 45.87,153.64C44.48,151.95 42.45,149.51 41.74,147.5C35.83,130.72 47.59,114.48 47.54,97.34C45.7,94.85 44.06,92.65 42.84,89.79C41.89,87.62 41.41,85.28 41.42,82.91C41.42,79.17 42.41,77.15 42.84,73.86C43.65,67.57 43.28,63.35 45.77,57.32C41.6,55.45 34.82,50.42 31.36,47.44C22.48,39.68 14.87,29.93 11.16,18.76ZM115.63,92.77C114.43,94.71 113.68,95.63 112.3,97.43C112.65,100.92 113.01,105.73 114.01,109.05C115.37,108.27 116.71,107.44 118,106.56C119.38,105.76 122.31,103.88 123.54,103.35C122.67,102.2 115.88,92.94 115.63,92.77Z"

    private static let plusPath = "M68.88,98.96H91.05 M79.96,87.88V110.05"
}

private struct VectorPathShape: Shape {

    let pathData: String
    let viewport: CGFloat

    func path(in rect: CGRect) -> Path {
        let scaleX = rect.width / viewport
        let scaleY = rect.height / viewport

        func point(_ x: Double, _ y: Double) -> CGPoint {
            CGPoint(x: rect.minX + CGFloat(x) * scaleX, y: rect.minY + CGFloat(y) * scaleY)
        }

        let tokens = Self.tokenize(pathData)
        var path = Path()
        var current = CGPoint.zero
        var index = 0

        func nextNumber() -> Double {
            defer { index += 1 }
            return index < tokens.count ? (Double(tokens[index]) ?? 0) : 0
        }

        while index < tokens.count {
            let command = tokens[index]
            index += 1

            switch command {
            case "M":
                let p = point(nextNumber(), nextNumber())
                path.move(to: p)
                current = p
            case "L":
                let p = point(nextNumber(), nextNumber())
                path.addLine(to: p)
                current = p
            case "H":
                let p = CGPoint(x: rect.minX + CGFloat(nextNumber()) * scaleX, y: current.y)
                path.addLine(to: p)
                current = p
            case "V":
                let p = CGPoint(x: current.x, y: rect.minY + CGFloat(nextNumber()) * scaleY)
                path.addLine(to: p)
                current = p
            case "C":
                let c1 = point(nextNumber(), nextNumber())
                let c2 = point(nextNumber(), nextNumber())
                let end = point(nextNumber(), nextNumber())
                path.addCurve(to: end, control1: c1, control2: c2)
                current = end
            case "Z":
                path.closeSubpath()
            default:
                break
            }
        }
        return path
    }

    private static func tokenize(_ data: String) -> [String] {
        var spaced = ""
        for character in data {
            if "MLHVCZ".contains(character) {
                spaced += " \(character) "
            } else if character == "," {
                spaced += " "
            } else {
                spaced.append(character)
            }
        }
        return spaced.split(whereSeparator: { $0 == " " }).map(String.init)
    }
}
