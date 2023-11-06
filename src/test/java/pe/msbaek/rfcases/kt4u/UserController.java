package pe.msbaek.rfcases.kt4u;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UserController {
    private final UserUseCase userUseCase;
    private final ExcelToDtoMapper excelToDtoMapper;

    public UserController(UserUseCase userUseCase, ExcelToDtoMapper excelToDtoMapper) {
        this.userUseCase = userUseCase;
        this.excelToDtoMapper = excelToDtoMapper;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/users/excel-upload",
            consumes = {"multipart/form-data"}
    )
    public ResponseEntity<Void> createUsersFromExcel(final MultipartFile file) {
        final List<CreateUserRequest> createUserRequests = excelToDtoMapper.read(file);
        final List<CreateUserRequest> results = createUserRequests.stream()
                .filter(r -> isCredentialsValid(r.loginId(), r.password(), r.username()))
                .toList();
        userUseCase.createUsers(results);
        return ResponseEntity.ok().build();
    }

    private boolean isCredentialsValid(final String cellLoginId, final String cellPasswd, final String cellUsername) {
        return StringUtils.hasText(cellLoginId) && StringUtils.hasText(cellPasswd) && StringUtils.hasText(cellUsername);
    }
}