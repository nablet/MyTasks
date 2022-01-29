import SwiftUI
import shared

@main
struct iOSApp: App {
    
    private let repositoryModule = RepositoryModule()
    
	var body: some Scene {
		WindowGroup {
			TasksScreen(repositoryModule: repositoryModule)
		}
	}
}
