import Foundation
import UIKit
import shared
import BackgroundTasks

class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate {
    // Run initializers on app launch
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        UNUserNotificationCenter.current().delegate = self
        Main_iosKt.doInitApp()
        registerBackgroundTasks()
        return true
    }
    
    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void
    ) {
        completionHandler([.list, .badge, .sound])
    }
    
    private func registerBackgroundTasks() {
        let koinIos = KoinIOS()
        let scheduler = koinIos.getScheduler()
        let chainExecutor = koinIos.getChainExecutor()
        let dispatcher = koinIos.getDynamicTaskDispatcher()
        
        BGTaskScheduler.shared.register(
            forTaskWithIdentifier: "kmp_master_dispatcher_task",
            using: nil,
        ) { task in
            IosBackgroundTaskHandler.shared.handleMasterDispatcherTask(
                task: task,
                dispatcher: dispatcher,
                scheduler: scheduler
            )
        }
        
        BGTaskScheduler.shared.register(
            forTaskWithIdentifier: "kmp_chain_executor_task",
            using: nil,
        ) { task in
            IosBackgroundTaskHandler.shared.handleChainExecutorTask(
                task: task,
                chainExecutor: chainExecutor
            )
        }
    }
}
