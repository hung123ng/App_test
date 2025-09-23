package utilities;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotHelper {
    private final WebDriver driver;
    private final String screenshotDir;
    private int stepCounter = 0;
    private boolean autoScreenshot = true;

    public ScreenshotHelper(WebDriver driver, String screenshotDir) {
        this.driver = driver;
        this.screenshotDir = screenshotDir;
        // Tạo thư mục nếu chưa tồn tại
        new File(screenshotDir).mkdirs();
    }

    /**
     * Chụp ảnh và attach vào Allure Report
     */
    @Attachment(value = "Screenshot: {actionName}", type = "image/png")
    public byte[] takeScreenshotForAllure(String actionName) {
        try {
            // 1. Dùng Ashot để chụp toàn bộ trang
            Screenshot fpScreenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.simple()) // Chiến lược chụp: cuộn và ghép ảnh
                    .takeScreenshot(driver);

            // 2. Chuyển ảnh từ BufferedImage sang byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(fpScreenshot.getImage(), "PNG", baos);
            byte[] screenshot = baos.toByteArray();

            // Lưu file local (tùy chọn, giữ lại logic cũ của bạn)
            if (screenshotDir != null && !screenshotDir.isEmpty()) {
                saveScreenshotToFile(screenshot, actionName);
            }

            // Attach vào Allure (đã được xử lý bởi @Attachment, chỉ cần return byte[])
            System.out.println("Successfully took a full-page screenshot for: " + actionName);
            return screenshot;

        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Chỉ chụp ảnh và lưu file
     */
    // public void takeScreenshot(String actionName) {
    //     try {
    //         byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    //         saveScreenshotToFile(screenshot, actionName);
    //     } catch (Exception e) {
    //         System.err.println("Failed to take screenshot: " + e.getMessage());
    //     }
    // }

    /**
     * Lưu screenshot vào file
     */
    private void saveScreenshotToFile(byte[] screenshot, String actionName) throws IOException {
        stepCounter++;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = String.format("%03d_%s_%s.png", stepCounter, actionName, timestamp);
        File screenshotFile = new File(screenshotDir, filename);
        FileUtils.writeByteArrayToFile(screenshotFile, screenshot);
        System.out.println("Screenshot saved: " + screenshotFile.getAbsolutePath());
    }

    // Getters and Setters
    public void setAutoScreenshot(boolean autoScreenshot) {
        this.autoScreenshot = autoScreenshot;
    }

    public boolean isAutoScreenshot() {
        return autoScreenshot;
    }
}