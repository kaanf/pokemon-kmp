import SwiftUI

extension Optional where Wrapped == UserInterfaceSizeClass {
    var isCompact: Bool { self == .compact }
}
