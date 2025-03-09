package SpringbootProject.dto.userEntityDTO;


public class UserDto {
    private Long id;
    private String fullName;
    private String genderUser;
    private String gmail;
    private String status;
    


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGenderUser() {
        return genderUser;
    }

    public void setGenderUser(String genderUser) {
        this.genderUser = genderUser;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", fullName=" + fullName + ", genderUser=" + genderUser + ", gmail=" + gmail
				+ ", status=" + status + "]";
	}
    
    
}