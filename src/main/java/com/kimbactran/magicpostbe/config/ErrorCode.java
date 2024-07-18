package com.kimbactran.magicpostbe.config;

public interface ErrorCode {
    String SUCCESS = "API-000";
    String SYSTEM_ERROR = "API-001";

    interface VALIDATE {
        String EXCEPTION = "VALID-001";
        String DUPLICATE = "VALID-002";
        String NOT_FOUND = "VALID-003";
        String INVALID_DATA = "VALID-004";
        String MAX_LENGTH = "VALID-005";
        String FOREIGN_KEY = "VALID-006";
        String NULL_OBJ = "VALID-007";
        String INVALID_URL = "VALID-008";
        String RESET_PASS_ERROR = "VALID-009";
        String LINK_EXISTS = "VALID-010";
        String FIELD_REQUIRE = "VALID-011";
        String NOT_NULL = "VALID_012";
        String DUPLICATE_USER_NAME = "VALID-013";
        String DUPLICATE_DEP_ID = "VALID-014";
        String NOT_AUTHEN = "VALID-015";
    }

    interface ERROR_MESSAGE {
        String DUPLICATE = "Bản ghi đã tồn tại";
        String NOT_FOUND = "Bản ghi không tồn tại";
        String NOT_CHANGE = "Chưa thay đổi";

        String NOT_SUBID_EMPTY ="-Mã thuê bao không được để trống";
        String NOT_SUBID="-Mã thuê bao không phải là thuê bao MOBIFONE còn hoạt động";
        String NOT_CONTENT_EMPTY ="-Nội dung không được để trống";
        String MAX_LENGTH = "-Nội dung nhập quá độ dài cho phép";
        String DUPLICATE_CONNECT = "-Trùng yêu cầu";
        String CAN_NOT_DOWLOAD_TEMPLATE = "Không thể tải template";
    }

    interface SCORING_ERROR_CODE {
        String EXISTED_OTHER_GROUP = "Đã tồn tại trong group khác";
        String NOT_ALLOWED_TO_EDIT = "Tiêu chí chấm điểm không được phép thêm/sửa/xóa";
    }
}
