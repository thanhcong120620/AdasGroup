
package SpringbootProject.entity.notSaving;

import java.util.List;
//import javax.persistence.Entity;
import java.util.function.Function;

//@Entity
public class ExcelObject   {

	public ExcelObject() {}
	
	
public ExcelObject(String column1, String column2, String column3, String column4, String column5, String column6,
			String column7, String column8, String column9, String column10, String column11, String column12,
			String column13, String column14, String column15, String column16, String column17, String column18,
			String column19, String column20) {
		
		this.column1 = column1;
		this.column2 = column2;
		this.column3 = column3;
		this.column4 = column4;
		this.column5 = column5;
		this.column6 = column6;
		this.column7 = column7;
		this.column8 = column8;
		this.column9 = column9;
		this.column10 = column10;
		this.column11 = column11;
		this.column12 = column12;
		this.column13 = column13;
		this.column14 = column14;
		this.column15 = column15;
		this.column16 = column16;
		this.column17 = column17;
		this.column18 = column18;
		this.column19 = column19;
		this.column20 = column20;
	}

//	private String phoneColumn;

	private String column1;

	private String column2;

	private String column3;

	private String column4;
	
	private String column5;

	private String column6;

	private String column7;
	
	private String column8;

	private String column9;

	private String column10;
	
	private String column11;

	private String column12;

	private String column13;
	
	private String column14;

	private String column15;

	private String column16;
	
	private String column17;

	private String column18;

	private String column19;
	
	private String column20;

//	public String getPhoneColumn() {
//		return phoneColumn;
//	}
//
//	public void setPhoneColumn(String phoneColmn) {
//		this.phoneColumn = phoneColmn;
//	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getColumn3() {
		return column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}

	public String getColumn4() {
		return column4;
	}

	public void setColumn4(String column4) {
		this.column4 = column4;
	}

	public String getColumn5() {
		return column5;
	}

	public void setColumn5(String column5) {
		this.column5 = column5;
	}

	public String getColumn6() {
		return column6;
	}

	public void setColumn6(String column6) {
		this.column6 = column6;
	}

	public String getColumn7() {
		return column7;
	}

	public void setColumn7(String column7) {
		this.column7 = column7;
	}

	public String getColumn8() {
		return column8;
	}

	public void setColumn8(String column8) {
		this.column8 = column8;
	}

	public String getColumn9() {
		return column9;
	}

	public void setColumn9(String column9) {
		this.column9 = column9;
	}

	public String getColumn10() {
		return column10;
	}

	public void setColumn10(String column10) {
		this.column10 = column10;
	}

	public String getColumn11() {
		return column11;
	}

	public void setColumn11(String column11) {
		this.column11 = column11;
	}

	public String getColumn12() {
		return column12;
	}

	public void setColumn12(String column12) {
		this.column12 = column12;
	}

	public String getColumn13() {
		return column13;
	}

	public void setColumn13(String column13) {
		this.column13 = column13;
	}

	public String getColumn14() {
		return column14;
	}

	public void setColumn14(String column14) {
		this.column14 = column14;
	}

	public String getColumn15() {
		return column15;
	}

	public void setColumn15(String column15) {
		this.column15 = column15;
	}

	public String getColumn16() {
		return column16;
	}

	public void setColumn16(String column16) {
		this.column16 = column16;
	}

	public String getColumn17() {
		return column17;
	}

	public void setColumn17(String column17) {
		this.column17 = column17;
	}

	public String getColumn18() {
		return column18;
	}

	public void setColumn18(String column18) {
		this.column18 = column18;
	}

	public String getColumn19() {
		return column19;
	}

	public void setColumn19(String column19) {
		this.column19 = column19;
	}

	public String getColumn20() {
		return column20;
	}

	public void setColumn20(String column20) {
		this.column20 = column20;
	}

	@Override
	public String toString() {
		return "ExcelObject [column1=" + column1 + ", column2=" + column2 + ", column3="
				+ column3 + ", column4=" + column4 + ", column5=" + column5 + ", column6=" + column6 + ", column7="
				+ column7 + ", column8=" + column8 + ", column9=" + column9 + ", column10=" + column10 + ", column11="
				+ column11 + ", column12=" + column12 + ", column13=" + column13 + ", column14=" + column14
				+ ", column15=" + column15 + ", column16=" + column16 + ", column17=" + column17 + ", column18="
				+ column18 + ", column19=" + column19 + ", column20=" + column20 + "]" +"\n";
	}

	
	
	
	//Note: Nếu sử file entity, hãy sửa các file phụ thuộc dữ liệu bởi UserEntity trong package (IOAlgorithm; GmailMKTAlgorithm)
	
	
	// --- Mảng/List chứa các tham chiếu đến phương thức getter ---
    
	
	
	// --- Mảng/List chứa các tham chiếu đến phương thức getter ---
    // Function<ExcelObject, String> đại diện cho một hàm nhận ExcelObject trả về String
    private static final List<Function<ExcelObject, String>> COLUMN_GETTERS = List.of(
            ExcelObject::getColumn1,  // Tham chiếu phương thức getColumn1
            ExcelObject::getColumn2,  // Tham chiếu phương thức getColumn2
            ExcelObject::getColumn3,
            ExcelObject::getColumn4,
            ExcelObject::getColumn5,
            ExcelObject::getColumn6,
            ExcelObject::getColumn7,
            ExcelObject::getColumn8,
            ExcelObject::getColumn9,
            ExcelObject::getColumn10,
            ExcelObject::getColumn11,
            ExcelObject::getColumn12,
            ExcelObject::getColumn13,
            ExcelObject::getColumn14,
            ExcelObject::getColumn15,
            ExcelObject::getColumn16,
            ExcelObject::getColumn17,
            ExcelObject::getColumn18,
            ExcelObject::getColumn19,
            ExcelObject::getColumn20  // Tham chiếu phương thức getColumn20
    );
    // Lưu ý: Khai báo static final để nó chỉ được tạo một lần cho cả class


    // --- Hàm mới sử dụng Method Reference ---
    /**
     * Lấy giá trị của cột tương ứng với index (1-based).
     * Sử dụng danh sách các Method Reference để gọi getter hiệu quả.
     *
     * @param index Số thứ tự cột (từ 1 đến 20).
     * @return Giá trị của cột dưới dạng String.
     * @throws IllegalArgumentException Nếu index nằm ngoài khoảng [1, 20].
     */
    public String getColumnByIndex(int index) {
        // Kiểm tra index hợp lệ (1-based)
        if (index < 1 || index > COLUMN_GETTERS.size()) {
             throw new IllegalArgumentException("Chỉ số cột phải nằm trong khoảng từ 1 đến " + COLUMN_GETTERS.size() + ". Chỉ số nhận được: " + index);
        }

        // Lấy hàm getter tương ứng từ danh sách (chuyển index 1-based thành 0-based)
        Function<ExcelObject, String> getter = COLUMN_GETTERS.get(index - 1);

        // Áp dụng hàm getter đó lên đối tượng hiện tại (this)
        return getter.apply(this);
    }
}
