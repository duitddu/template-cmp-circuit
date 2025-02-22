import Foundation
import KakaoSDKUser
import KakaoSDKAuth
import KakaoSDKCommon

@objcMembers public class KakaoLoginBridge: NSObject {
    public func request(
        success: @escaping (String) -> Void,
        failure: @escaping () -> Void,
        cancel: @escaping () -> Void
    ) {
        if UserApi.isKakaoTalkLoginAvailable() {
            UserApi.shared.loginWithKakaoTalk { [weak self] (oauthToken, error) in
                self?.handle(oauthToken: oauthToken, error: error, success: success, failure: failure, cancel: cancel)
            }
        } else {
            UserApi.shared.loginWithKakaoAccount { [weak self] (oauthToken, error) in
                self?.handle(oauthToken: oauthToken, error: error, success: success, failure: failure, cancel: cancel)
            }
        }
    }
    
    private func handle(
        oauthToken: OAuthToken?,
        error: Error?,
        success: @escaping (String) -> Void,
        failure: @escaping () -> Void,
        cancel: @escaping () -> Void
    ) {
        if let error = error {
            if let error = error as? SdkError {
                switch error {
                case .ClientFailed(let reason, _):
                    if reason == .Cancelled {
                        cancel()
                    } else {
                        failure()
                    }
                    
                default:
                    failure()
                }
            } else {
                failure()
            }
        } else {
            if let accessToken = oauthToken?.accessToken {
                success(accessToken)
            } else {
                failure()
            }
        }
    }
}
