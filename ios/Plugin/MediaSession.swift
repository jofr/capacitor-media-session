import Foundation

@objc public class MediaSession: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
