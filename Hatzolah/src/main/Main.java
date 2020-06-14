package main;

import java.sql.Connection;
import java.sql.DriverManager;

import gui.AddBranchWindow;
import gui.AddBusWindow;
import gui.AddCallMemsWindow;
import gui.AddCallSympsWindow;
import gui.AddCallWindow;
import gui.AddDonationWindow;
import gui.AddDonorWindow;
import gui.AddMemCredsWindow;
import gui.AddMemberWindow;
import gui.DeleteBranchWindow;
import gui.DeleteBusWindow;
import gui.DeleteDonorWindow;
import gui.DeleteMemberWindow;
import gui.PurchaseEquipWindow;
import gui.PurchaseSuppliesWindow;
import gui.UpdateBusWindow;
import gui.UpdateDonorWindow;
import gui.UpdateMemberWindow;
import gui.ViewBranchInfoWindow;
import gui.ViewBranchesWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage mainWindow) throws Exception {
		String url = "jdbc:sqlserver://localhost:1433;databaseName=hatzolah;integratedSecurity=true";
		Connection dbConnection = DriverManager.getConnection(url);

		mainWindow.setTitle("Main Menu");
		mainWindow.setHeight(300);
		mainWindow.setWidth(700);
		
		Menu branchMenu = new Menu("Branches");
		Menu busMenu = new Menu("Buses");
		Menu purchasesMenu = new Menu("Purchases");
		Menu memberMenu = new Menu("Members");
		Menu donorMenu = new Menu("Donors");
		Menu donationMenu = new Menu("Donations");
		Menu callMenu = new Menu("Calls");
		
		MenuItem addBranchItem = new MenuItem("Add Branch");
		MenuItem deleteBranchItem = new MenuItem("Delete Branch");
		MenuItem viewBranchesItem = new MenuItem("View Branches");
		MenuItem viewBranchInfoItem = new MenuItem("View Branch Info");		
		MenuItem addBusItem = new MenuItem("Add Bus");
		MenuItem deleteBusItem = new MenuItem("Delete Bus");
		MenuItem updateBusItem = new MenuItem("Update Bus");
		MenuItem purchaseSuppliesItem = new MenuItem("Purchase Supplies");
		MenuItem purchaseEquipmentItem = new MenuItem("Purchase Equipment");
		MenuItem addMemberItem = new MenuItem("Add Member");
		MenuItem deleteMemberItem = new MenuItem("Delete Member");
		MenuItem updateMemberItem = new MenuItem("Update Member");
		MenuItem addMemCredsItem = new MenuItem("Add Member Credentials");
		MenuItem addDonorItem = new MenuItem("Add Donor");
		MenuItem deleteDonorItem = new MenuItem("Delete Donor");
		MenuItem updateDonorItem = new MenuItem("Update Donor Address");
		MenuItem addDonationItem = new MenuItem("Add Donation");
		MenuItem addCallItem = new MenuItem("Add Call");
		MenuItem addCallSympsItem = new MenuItem("Add Call Symptoms");
		MenuItem addCallMemsItem = new MenuItem("Add Call Members");
		
		branchMenu.getItems().addAll(addBranchItem, deleteBranchItem, viewBranchesItem, viewBranchInfoItem);
		busMenu.getItems().addAll(addBusItem, deleteBusItem, updateBusItem);
		purchasesMenu.getItems().addAll(purchaseSuppliesItem, purchaseEquipmentItem);
		memberMenu.getItems().addAll(addMemberItem, deleteMemberItem, updateMemberItem, addMemCredsItem);
		donorMenu.getItems().addAll(addDonorItem, deleteDonorItem, updateDonorItem);
		donationMenu.getItems().addAll(addDonationItem);
		callMenu.getItems().addAll(addCallItem, addCallSympsItem, addCallMemsItem);
		
		addBranchItem.setOnAction(e -> new AddBranchWindow(dbConnection));
		deleteBranchItem.setOnAction(e -> new DeleteBranchWindow(dbConnection));
		viewBranchesItem.setOnAction(e -> new ViewBranchesWindow(dbConnection));
		viewBranchInfoItem.setOnAction(e -> new ViewBranchInfoWindow(dbConnection));
		addBusItem.setOnAction(e -> new AddBusWindow(dbConnection));
		deleteBusItem.setOnAction(e -> new DeleteBusWindow(dbConnection));
		updateBusItem.setOnAction(e -> new UpdateBusWindow(dbConnection));
		purchaseSuppliesItem.setOnAction(e -> new PurchaseSuppliesWindow(dbConnection));
		purchaseEquipmentItem.setOnAction(e -> new PurchaseEquipWindow(dbConnection));
		addMemberItem.setOnAction(e -> new AddMemberWindow(dbConnection));
		deleteMemberItem.setOnAction(e -> new DeleteMemberWindow(dbConnection));
		updateMemberItem.setOnAction(e -> new UpdateMemberWindow(dbConnection));
		addMemCredsItem.setOnAction(e -> new AddMemCredsWindow(dbConnection));
		addDonorItem.setOnAction(e -> new AddDonorWindow(dbConnection));
		deleteDonorItem.setOnAction(e -> new DeleteDonorWindow(dbConnection));
		updateDonorItem.setOnAction(e -> new UpdateDonorWindow(dbConnection));
		addDonationItem.setOnAction(e -> new AddDonationWindow(dbConnection));
		addCallItem.setOnAction(e -> new AddCallWindow(dbConnection));
		addCallSympsItem.setOnAction(e -> new AddCallSympsWindow(dbConnection));
		addCallMemsItem.setOnAction(e -> new AddCallMemsWindow(dbConnection));
		
		MenuBar bar = new MenuBar();
		bar.getMenus().add(branchMenu);
		bar.getMenus().add(busMenu);
		bar.getMenus().add(purchasesMenu);
		bar.getMenus().add(memberMenu);
		bar.getMenus().add(donorMenu);
		bar.getMenus().add(donationMenu);
		bar.getMenus().add(callMenu);

		BorderPane borderLayout = new BorderPane();
		borderLayout.setTop(bar);
		Scene scene = new Scene(borderLayout);
		mainWindow.setScene(scene);
		mainWindow.show();
	}
}
