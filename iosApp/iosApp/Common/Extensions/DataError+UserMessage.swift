import Foundation
import SharedLogic

extension DataError {
    var userMessage: String {
        if let remote = self as? DataErrorRemote {
            return remote.message
        }
        if let local = self as? DataErrorLocal {
            return local.message
        }
        return String(localized: "error.unknown")
    }
}

private extension DataErrorRemote {
    var message: String {
        switch self {
        case .noInternet:
            return String(localized: "error.no_internet")
        case .requestTimeout:
            return String(localized: "error.request_timeout")
        case .tooManyRequests:
            return String(localized: "error.too_many_requests")
        case .notFound:
            return String(localized: "error.not_found")
        case .serverError, .serviceUnavailable:
            return String(localized: "error.server")
        case .serialization:
            return String(localized: "error.serialization")
        default:
            return String(localized: "error.unknown")
        }
    }
}

private extension DataErrorLocal {
    var message: String {
        switch self {
        case .diskFull:
            return String(localized: "error.disk_full")
        default:
            return String(localized: "error.unknown")
        }
    }
}
