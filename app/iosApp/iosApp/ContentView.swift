import UIKit
import SwiftUI
import Translation
import shared

struct ComposeView: UIViewControllerRepresentable {
    let translationBridge: Ui_componentsTranslationBridge

    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController(translationBridge: translationBridge)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    @State private var translationBridge = Ui_componentsTranslationBridge()
    @State private var textToTranslate: String? = nil
    @State private var isTranslationPresented = false

    var body: some View {
        ComposeView(translationBridge: translationBridge)
            .ignoresSafeArea()
            .onAppear {
                translationBridge.onTranslateRequested = { text in
                    self.textToTranslate = text
                    self.isTranslationPresented = true
                }
            }
            .translationPresentation(
                isPresented: $isTranslationPresented,
                text: textToTranslate ?? ""
            )
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
