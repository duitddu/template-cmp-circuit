import UIKit
import NidThirdPartyLogin
import KakaoSDKAuth
import KakaoSDKCommon

class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        NidOAuth.shared.setLoginBehavior(.appPreferredWithInAppBrowserFallback)
        NidOAuth.shared.initialize()
        
        KakaoSDK.initSDK(appKey: "YOUR_KAKAO_APP_KEY")
        
        return true
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        if NidOAuth.shared.handleURL(url) {
          return true
        }
        
        if AuthApi.isKakaoTalkLoginUrl(url) {
            return AuthController.handleOpenUrl(url: url)
        }
      
        return false
    }
}
