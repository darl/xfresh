namespace java ru.darlz.ff.thrift

struct RemoteInternalRequest {
    1: string realPath,
    2: bool needTransform,
    3: map<string, string> cookies,
    4: map<string, list<string>> parameters,
    5: string requestUrl,
    6: string queryString,
    7: string remoteAddr,
    8: map<string, string> headers,
    9: i64 userId
}

struct RemoteInternalResponse {
    1: string redirectTo,
    2: map<string, string> cookies,
    3: i32 httpStatus,
    4: list<string> data,
    5: list<string> errors,
    6: map<string, string> attributes,
    7: map<string, string> headers
}

service RemoteYaletProcessor {
    RemoteInternalResponse process(1: string yalet, 2: RemoteInternalRequest req, 3: RemoteInternalResponse res)
}
