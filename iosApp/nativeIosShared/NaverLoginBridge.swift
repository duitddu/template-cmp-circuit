import Foundation
import NidThirdPartyLogin

@objcMembers public class NaverLoginBridge: NSObject {

    public func request(
        success: @escaping (String) -> Void,
        failure: @escaping () -> Void,
        cancel: @escaping () -> Void
    ) {
        NidOAuth.shared.requestLogin { ret in
            switch ret {
            case .success(let loginResult):
                success(loginResult.accessToken.tokenString)
                
            case .failure(let error):
                switch error {
                case .clientError(let detail):
                    switch detail {
                    case .canceledByUser:
                        cancel()
                        
                    default:
                        failure()
                    }
                    
                default:
                    failure()
                }
            }
        }
    }
}
