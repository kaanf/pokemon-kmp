import KMPNativeCoroutinesAsync
import KMPNativeCoroutinesCore
import SharedLogic
import SwiftUI

extension View {
    func collect<Output>(
        _ flow: @escaping NativeFlow<Output, Swift.Error, KotlinUnit>,
        onEach: @escaping (Output) -> Void
    ) -> some View {
        task {
            do {
                for try await element in asyncSequence(for: flow) {
                    onEach(element)
                }
            } catch {}
        }
    }
}
