package HW4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrUserResponse{
    private String spoonacularPassword;
    private String hash;
    private String status;
    private String username;
}