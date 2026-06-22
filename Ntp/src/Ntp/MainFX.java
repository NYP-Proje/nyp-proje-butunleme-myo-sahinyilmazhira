package Ntp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainFX extends Application {

    private Veritabanı db;
    private GuvenlikServisi guvenlik;
    private Stage stage;
    
    // Table components
    private TableView<Gider> tableView;
    private ObservableList<Gider> observableGiderler;
    
    // Summary components
    private Label totalExpenseLabel;
    private Label categoryReportLabel;
    
    // Form fields
    private TextField idField;
    private TextField miktarField;
    private TextField kategoriField;
    private TextField tarihField;
    private TextField aciklamaField;
    
    // Current Logged In User
    private String currentUser = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.db = new Veritabanı();
        this.guvenlik = new GuvenlikServisi();

        primaryStage.setTitle("Gider Takip Sistemi v2.0 - FX");
        
        // Show register page if no users, otherwise show login
        if (!guvenlik.kayitliKullaniciVarMi()) {
            showRegisterScene();
        } else {
            showLoginScene();
        }
        
        primaryStage.show();
    }

    // --- SCENE 1: KAYIT EKRANI ---
    private void showRegisterScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getStyleClass().add("root");
        
        VBox card = new VBox(15);
        card.getStyleClass().add("glass-panel");
        card.setMaxWidth(400);
        card.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("KULLANICI KAYDI");
        titleLabel.getStyleClass().add("title-label");
        
        Label descLabel = new Label("Sistemi ilk kez başlatmak için yönetici hesabı oluşturun.");
        descLabel.getStyleClass().add("subtitle-label");
        descLabel.setWrapText(true);
        descLabel.setAlignment(Pos.CENTER);
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Kullanıcı Adı");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Şifre");
        
        Button registerButton = new Button("Kayıt Ol & Başlat");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        
        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
        
        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            
            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Lütfen tüm alanları doldurun!");
            } else {
                guvenlik.kayitOl(username, password);
                currentUser = username;
                showDashboardScene();
            }
        });
        
        card.getChildren().addAll(titleLabel, descLabel, usernameField, passwordField, registerButton, statusLabel);
        root.getChildren().add(card);
        
        Scene scene = new Scene(root, 900, 650);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }

    // --- SCENE 2: GİRİŞ EKRANI ---
    private void showLoginScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getStyleClass().add("root");
        
        VBox card = new VBox(15);
        card.getStyleClass().add("glass-panel");
        card.setMaxWidth(400);
        card.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("KULLANICI GİRİŞİ");
        titleLabel.getStyleClass().add("title-label");
        
        Label descLabel = new Label("Gider Takip Sistemine erişmek için bilgilerinizi girin.");
        descLabel.getStyleClass().add("subtitle-label");
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Kullanıcı Adı");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Şifre");
        
        Button loginButton = new Button("Giriş Yap");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        
        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
        
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            
            if (guvenlik.girisYap(username, password)) {
                currentUser = username;
                showDashboardScene();
            } else {
                statusLabel.setText("Hatalı kullanıcı adı veya şifre!");
            }
        });
        
        card.getChildren().addAll(titleLabel, descLabel, usernameField, passwordField, loginButton, statusLabel);
        root.getChildren().add(card);
        
        Scene scene = new Scene(root, 900, 650);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }

    // --- SCENE 3: DASHBOARD EKRANI ---
    private void showDashboardScene() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");
        
        // --- TOP BAR (Header) ---
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: rgba(15, 15, 27, 0.85); -fx-border-color: rgba(255, 255, 255, 0.05); -fx-border-width: 0 0 1px 0;");
        
        Label systemTitle = new Label("GİDER TAKİP SİSTEMİ");
        systemTitle.getStyleClass().add("title-label");
        systemTitle.setStyle("-fx-font-size: 20px;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label userLabel = new Label("Kullanıcı: " + currentUser);
        userLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #a78bfa; -fx-font-size: 14px;");
        
        Button logoutBtn = new Button("Çıkış Yap");
        logoutBtn.getStyleClass().clear();
        logoutBtn.getStyleClass().add("button-secondary");
        logoutBtn.setOnAction(e -> showLoginScene());
        
        topBar.getChildren().addAll(systemTitle, spacer, userLabel, logoutBtn);
        root.setTop(topBar);
        
        // --- CENTER AREA (Table and Form) ---
        GridPane centerGrid = new GridPane();
        centerGrid.setPadding(new Insets(24));
        centerGrid.setHgap(24);
        centerGrid.setVgap(24);
        
        // Column Constraints (Left Panel: 40% Width, Right Panel: 60% Width)
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(38);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(62);
        centerGrid.getColumnConstraints().addAll(col1, col2);
        
        // Row Constraints (Allowing vertical stretching)
        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.ALWAYS);
        centerGrid.getRowConstraints().add(row1);
        
        // --- LEFT PANEL: FORM & REPORTS ---
        VBox leftPanel = new VBox(20);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        
        // Expense Input Form
        VBox formCard = new VBox(12);
        formCard.getStyleClass().add("glass-panel");
        
        Label formTitle = new Label("Gider Ekle / Düzenle");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #a78bfa;");
        
        idField = new TextField();
        idField.setPromptText("Gider ID (Güncelleme ve Silme için)");
        idField.setEditable(false);
        idField.setDisable(true); // Auto-populated when table row is selected
        idField.setStyle("-fx-opacity: 0.7;");
        
        miktarField = new TextField();
        miktarField.setPromptText("Miktar (TL)");
        
        kategoriField = new TextField();
        kategoriField.setPromptText("Kategori (örn: Gıda, Kira, Fatura)");
        
        tarihField = new TextField();
        tarihField.setPromptText("Tarih (GG.AA.YYYY)");
        
        aciklamaField = new TextField();
        aciklamaField.setPromptText("Açıklama");
        
        HBox formButtons = new HBox(10);
        formButtons.setAlignment(Pos.CENTER);
        
        Button addBtn = new Button("Ekle");
        addBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(addBtn, Priority.ALWAYS);
        
        Button updateBtn = new Button("Güncelle");
        updateBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(updateBtn, Priority.ALWAYS);
        updateBtn.getStyleClass().clear();
        updateBtn.getStyleClass().add("button-secondary");
        
        Button clearBtn = new Button("Temizle");
        clearBtn.getStyleClass().clear();
        clearBtn.getStyleClass().add("button-secondary");
        
        formButtons.getChildren().addAll(addBtn, updateBtn, clearBtn);
        
        Button deleteBtn = new Button("Seçili Gideri Sil");
        deleteBtn.getStyleClass().clear();
        deleteBtn.getStyleClass().add("button-danger");
        deleteBtn.setMaxWidth(Double.MAX_VALUE);
        
        formCard.getChildren().addAll(formTitle, idField, miktarField, kategoriField, tarihField, aciklamaField, formButtons, deleteBtn);
        
        // Category Summary Card
        VBox summaryCard = new VBox(10);
        summaryCard.getStyleClass().add("summary-card");
        
        Label summaryTitle = new Label("TOPLAM HARCAMA");
        summaryTitle.getStyleClass().add("summary-title");
        
        totalExpenseLabel = new Label("0.00 TL");
        totalExpenseLabel.getStyleClass().add("summary-value");
        
        HBox categorySearchBox = new HBox(8);
        categorySearchBox.setAlignment(Pos.CENTER_LEFT);
        
        TextField catQueryField = new TextField();
        catQueryField.setPromptText("Kategori Raporu Sorgula");
        HBox.setHgrow(catQueryField, Priority.ALWAYS);
        
        Button catQueryBtn = new Button("Sorgula");
        catQueryBtn.getStyleClass().clear();
        catQueryBtn.getStyleClass().add("button-secondary");
        catQueryBtn.setPadding(new Insets(8, 12, 8, 12));
        
        categorySearchBox.getChildren().addAll(catQueryField, catQueryBtn);
        
        categoryReportLabel = new Label("Arama yapmak için kategori adı girin.");
        categoryReportLabel.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 13px;");
        
        summaryCard.getChildren().addAll(summaryTitle, totalExpenseLabel, categorySearchBox, categoryReportLabel);
        
        leftPanel.getChildren().addAll(formCard, summaryCard);
        centerGrid.add(leftPanel, 0, 0);
        
        // --- RIGHT PANEL: TABLE VIEW ---
        VBox rightPanel = new VBox(15);
        rightPanel.getStyleClass().add("glass-panel");
        
        Label tableTitle = new Label("Kayıtlı Giderler");
        tableTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
        
        tableView = new TableView<>();
        VBox.setVgrow(tableView, Priority.ALWAYS);
        
        TableColumn<Gider, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);
        
        TableColumn<Gider, Double> colMiktar = new TableColumn<>("Miktar (TL)");
        colMiktar.setCellValueFactory(new PropertyValueFactory<>("miktar"));
        colMiktar.setPrefWidth(100);
        
        TableColumn<Gider, String> colKategori = new TableColumn<>("Kategori");
        colKategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        colKategori.setPrefWidth(120);
        
        TableColumn<Gider, String> colTarih = new TableColumn<>("Tarih");
        colTarih.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        colTarih.setPrefWidth(110);
        
        TableColumn<Gider, String> colAciklama = new TableColumn<>("Açıklama");
        colAciklama.setCellValueFactory(new PropertyValueFactory<>("aciklama"));
        colAciklama.setPrefWidth(180);
        
        tableView.getColumns().addAll(colId, colMiktar, colKategori, colTarih, colAciklama);
        
        rightPanel.getChildren().addAll(tableTitle, tableView);
        centerGrid.add(rightPanel, 1, 0);
        
        root.setCenter(centerGrid);
        
        // --- DATA BINDING & EVENTS ---
        
        // Populate table
        observableGiderler = FXCollections.observableArrayList();
        tableView.setItems(observableGiderler);
        refreshData();
        
        // Table row selection event
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(String.valueOf(newSelection.getId()));
                miktarField.setText(String.valueOf(newSelection.getMiktar()));
                kategoriField.setText(newSelection.getKategori());
                tarihField.setText(newSelection.getTarih());
                aciklamaField.setText(newSelection.getAciklama());
            }
        });
        
        // Clear fields
        clearBtn.setOnAction(e -> clearFormFields());
        
        // Add expense action
        addBtn.setOnAction(e -> {
            try {
                double miktar = Double.parseDouble(miktarField.getText());
                String kategori = kategoriField.getText().trim();
                String tarih = tarihField.getText().trim();
                String aciklama = aciklamaField.getText().trim();
                
                if (kategori.isEmpty() || tarih.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen Kategori ve Tarih alanlarını doldurunuz!");
                    return;
                }
                
                db.giderEkle(miktar, kategori, tarih, aciklama);
                clearFormFields();
                refreshData();
                showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Gider başarıyla eklendi.");
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen miktar alanına geçerli bir sayı giriniz!");
            }
        });
        
        // Update expense action
        updateBtn.setOnAction(e -> {
            String idStr = idField.getText();
            if (idStr.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen güncellemek istediğiniz gideri tablodan seçiniz!");
                return;
            }
            
            try {
                int id = Integer.parseInt(idStr);
                double miktar = Double.parseDouble(miktarField.getText());
                String kategori = kategoriField.getText().trim();
                String tarih = tarihField.getText().trim();
                String aciklama = aciklamaField.getText().trim();
                
                if (kategori.isEmpty() || tarih.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Uyarı", "Kategori ve Tarih boş bırakılamaz!");
                    return;
                }
                
                boolean updated = db.giderGuncelle(id, miktar, kategori, tarih, aciklama);
                if (updated) {
                    refreshData();
                    showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Gider başarıyla güncellendi.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Hata", "Gider güncellenemedi!");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Hata", "Lütfen bilgileri geçerli formatlarda giriniz!");
            }
        });
        
        // Delete expense action
        deleteBtn.setOnAction(e -> {
            String idStr = idField.getText();
            if (idStr.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen silmek istediğiniz gideri tablodan seçiniz!");
                return;
            }
            
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Silme Onayı");
            confirmAlert.setHeaderText("Bu gider kaydı kalıcı olarak silinecektir.");
            confirmAlert.setContentText("ID: " + idStr + " olan gideri silmek istediğinizden emin misiniz?");
            
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    int id = Integer.parseInt(idStr);
                    boolean deleted = db.giderSil(id);
                    if (deleted) {
                        clearFormFields();
                        refreshData();
                        showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Gider kaydı silindi.");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Hata", "Gider kaydı bulunamadı veya silinemedi!");
                    }
                }
            });
        });
        
        // Category Search action
        catQueryBtn.setOnAction(e -> {
            String query = catQueryField.getText().trim();
            if (query.isEmpty()) {
                categoryReportLabel.setText("Lütfen kategori adı girin.");
                return;
            }
            double totalForCategory = db.kategoriyeGoreToplam(query);
            categoryReportLabel.setText(String.format("'%s' Toplam: %.2f TL", query, totalForCategory));
        });
        
        Scene scene = new Scene(root, 1050, 720);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
    }
    
    // --- HELPER METHODS ---
    
    private void clearFormFields() {
        idField.clear();
        miktarField.clear();
        kategoriField.clear();
        tarihField.clear();
        aciklamaField.clear();
        tableView.getSelectionModel().clearSelection();
    }
    
    private void refreshData() {
        ArrayList<Gider> list = db.getGiderler();
        observableGiderler.setAll(list);
        
        // Calculate total
        double total = 0.0;
        for (Gider g : list) {
            total += g.getMiktar();
        }
        totalExpenseLabel.setText(String.format("%.2f TL", total));
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Custom styling for dialogs if they inherit CSS is sometimes tricky, but they will show cleanly
        alert.showAndWait();
    }
}
