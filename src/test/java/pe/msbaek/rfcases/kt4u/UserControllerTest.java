package pe.msbaek.rfcases.kt4u;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private List<CreateUserRequest> requestedUsers;

    @Test
    void createUsersFromExcel() {
        UserUseCase useCase = createUserRequests -> requestedUsers = createUserRequests;
        ExcelToDtoMapper mapper = new CreateUserExcelToDtoMapper() {
            @Override
            List<CreateUserRequest> read(MultipartFile file) {
                return List.of(new CreateUserRequest("loginId", "passwd", "username", "boLoginId"));
            }

            @Override
            CreateUserRequest toDto(Sheet worksheet, int rowIndex) {
                throw new UnsupportedOperationException("::toDto not implemented yet");
            }
        };

        UserController controller = new UserController(useCase, mapper);

        controller.createUsersFromExcel(null);

        assertThat(requestedUsers.get(0).toString()).isEqualTo("CreateUserRequest[loginId=loginId, password=passwd, username=username, boLoginId=boLoginId]");
    }
}