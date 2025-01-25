package SpringbootProject.controller.WebController;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ProgressController {

    private final SimpMessagingTemplate messagingTemplate;

    public ProgressController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/startProgress")
    public void startProgress() throws InterruptedException {
        int totalUpdates = 10; // Tổng số lần cập nhật
        for (int i = 1; i <= totalUpdates; i++) {
            Thread.sleep(5000); // Mô phỏng thời gian cho mỗi cập nhật
            int progress = (i * 100) / totalUpdates;
            long timeRemaining = (totalUpdates - i) * 5; // Thời gian còn lại (giây)
            
            // Gửi tiến trình cập nhật đến client
            ProgressUpdate progressUpdate = new ProgressUpdate(i, progress, timeRemaining);
            messagingTemplate.convertAndSend("/topic/progress", progressUpdate);
        }
    }
}


