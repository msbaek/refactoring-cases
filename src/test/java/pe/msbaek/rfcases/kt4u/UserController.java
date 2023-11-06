package pe.msbaek.rfcases.kt4u;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserController {
    private final UserUseCase userUseCase;
    private final ExcelToDtoMapper excelToDtoMapper = new CreateUserExcelToDtoMapper();

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/users/excel-upload",
            consumes = {"multipart/form-data"}
    )
    public ResponseEntity<Void> createUsersFromExcel(final MultipartFile file) {
        final List<CreateUserRequest> createUserRequests = excelToDtoMapper.read(file);
        final List<CreateUserRequest> results = filterInvalidCredentials(createUserRequests);
        userUseCase.createUsers(results);
        return ResponseEntity.ok().build();
    }

    private List<CreateUserRequest> filterInvalidCredentials(List<CreateUserRequest> createUserRequests) {
        final List<CreateUserRequest> results = new ArrayList<>();
        for (CreateUserRequest createUserRequest : createUserRequests) {
            // Check if the row is empty
            if (isCredentialsValid(createUserRequest.loginId(), createUserRequest.password(), createUserRequest.username())) {
                results.add(createUserRequest);
            }
        }
        return results;
    }

    private boolean isCredentialsValid(final String cellLoginId, final String cellPasswd, final String cellUsername) {
        return StringUtils.hasText(cellLoginId) && StringUtils.hasText(cellPasswd) && StringUtils.hasText(cellUsername);
    }
}