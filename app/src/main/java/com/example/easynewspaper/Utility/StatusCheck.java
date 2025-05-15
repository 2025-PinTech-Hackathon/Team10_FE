package com.example.easynewspaper.Utility;

import android.widget.Toast;

import com.example.easynewspaper.DataStruct.Status;

public class StatusCheck {
    public static Status isSuccess(int code) {
        if (code == 200) {
            return new Status(true);
        }
        else {
            Status status = new Status(false);

            switch (code) {
                case 400:
                    status.msg = "가입되지 않은 계정입니다.";
                    break;
                case 401:
                    status.msg = "인증되지 않았습니다. 올바른 인증 정보를 제공해주세요.";
                    break;
                case 403:
                    status.msg = "요청한 작업을 수행하기 위한 권한이 없습니다.";
                    break;
                case 404:
                    status.msg = "서버에 문제가 발생했습니다. 잠시후 다시 시도해주세요.";
                    break;
                case 405:
                    status.msg = "서버에 문제가 발생했습니다. 잠시후 다시 시도해주세요.";
                    break;
                case 419:
                    status.msg = "인증이 만료되었습니다. 다시 로그인해주세요.";
                    break;
                case 429:
                    status.msg = "요청횟수가 제한을 초과하였습니다. 잠시 후 다시 시도해주세요.";
                    break;
                case 500:
                    status.msg = "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.";
                    break;
                case 503:
                    status.msg = "서비스 점검 중입니다. 잠시 후 다시 시도해주세요.";
                    break;
                case 504:
                    status.msg = "외부 서비스와의 연결이 지연되었습니다. 자밋 후 다시 시도해주세요.";
                    break;
                default:
                    status.msg = "연결에 실패했습니다.";
                    break;
            }

            return status;
        }
    }
}
