🛠️ Tech Stack & Multiplatform Tools
Language: Kotlin (100% Shared Business Logic)

Multiplatform UI: Compose Multiplatform (CMP) * Target Platforms: Android, iOS, and Desktop (JVM)

Architecture: Clean Architecture + MVI (Model-View-Intent)

Dependency Injection: Koin (Multiplatform version)

Networking: Ktor Client (Multiplatform engine)

Local Persistence: SQLDelight (or Room Multiplatform)

Image Loading: Coil3 (Multiplatform support)

Concurrency: Kotlin Coroutines & Flow

🏗️ Multiplatform Architecture
This project utilizes a Single Codebase strategy where ~90% of the code is shared in the :composeApp module:

commonMain: Contains the Clean Architecture core (Use Cases, Repositories, and MVI ViewModels) and the CMP UI code.

androidMain: Native Android configuration and Manifest.

iosMain: Interop code to initialize the Compose UI inside a SwiftUI UIViewController.

desktopMain: Configuration for the JVM-based desktop application.

🚀 Key Multiplatform Features
Expect/Actual Pattern: Used for platform-specific implementations (like opening a browser or local file pickers).

Shared UI Components: High-fidelity UI rendering across all three platforms using a single Compose implementation.

Responsive Layouts: Adaptive UI design that adjusts gracefully from mobile screens to Desktop windows.
![phonebook](https://github.com/user-attachments/assets/1597ac11-b2dd-46a4-906c-c7125561a29f)
![deskbook](https://github.com/user-attachments/assets/244f1e59-d9c6-4c87-8639-795cc9bc1561)
