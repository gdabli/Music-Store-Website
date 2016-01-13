package cs636.music.presentation;

import java.io.IOException;
import java.io.PrintStream;

import java.util.Scanner;
import java.util.Set;

import cs636.music.config.MusicSystemConfig;
import cs636.music.domain.Cart;
import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.domain.LineItem;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;

public class PresentationUtils {

	public static void displayInvoices(Set<Invoice> invList, PrintStream out) {
		out.println("---------- Invoices--------------");
		out.println("\nId\tUser\tInvoice Date\tAmount\t#LineItems");
		for (Invoice i : invList) {
			out.print("\n" + i.getInvoiceId() + "\t"
					+ i.getUser().getEmailAddress() + "\t" + i.getInvoiceDate()
					+ "\t" + i.getTotalAmount() + "\t"
					+ i.getLineItems().size());
		}
		out.println("\n-------------------------------------");

	}

	public static void displayProductInfo(Product p, PrintStream out) {
		out.println("----------PRODUCT INFORMATION--------------\n");
		out.print("\nProductCode : " + p.getCode() + "\n Description : "
				+ p.getDescription() + "\n Price : " + p.getPrice());

		out.println("\n----------PRODUCT INFORMATION--------------\n");
	}

	public static void displayCDCatlog(Set<Product> cds, PrintStream out) {
		out.println("----------CD CATALOG--------------");
		out.println("\n Prod Code \t Product Description");
		for (Product cd : cds) {
			out.print("\n" + cd.getCode() + "\t" + cd.getDescription());
		}
		out.println("\n-------------------------------------");
	}

	public static void downloadReport(Set<Download> download, PrintStream out) {
		out.println("\n-----------------Download Report--------------");
		out.println("\nDL Id \tProd Code\tFilename\tUser\tDL Date");
		for (Download d : download) {

			out.print("\n\t" + d.getDownloadId() + "\t"
					+ d.getTrack().getProduct().getCode() + "\t" + d.getTrack().getSampleFilename()
					+ "\t\t " + d.getUser().getEmailAddress() + "\t"
					+ d.getDownloadDate());
		}
		out.println("\n---------------------------------------------------");
	}

	public static void displayCart(Cart cart, PrintStream out) {
		out.println("\n-----------------C A R T--------------");
		if (cart == null)
			out.println("Null cart!");
		else {
			out.println("\n\tProd Code\tQty");
			for (LineItem item : cart.getItems()) {
				out.print("\n\t" + item.getProduct().getCode() + "\t"
						+ item.getQuantity());
			}
		}
		out.println("\n---------------------------------------------------");
	}

	public static void displayTracks(Product product, PrintStream out) {
		Set<Track> tracks = product.getTracks();
		out.println("\n-----------------TRACKS for " + product.getCode()
				+ "--------------");
		out.println("\n\tTrackNo\tFileName\tTitle\tProd Code");
		for (Track t : tracks) {
			out.print("\n\t" + t.getTrackNumber() + "\t"
					+ t.getSampleFilename() + "\t" + t.getTitle() + "\t "
					+ product.getCode());
		}
		out.println("\n---------------------------------------------------");
	}

	public static void playTrack(Track track, PrintStream out) {
		out.println("\n---------------------------------------------------");
		out.println("\n Track URL: " + MusicSystemConfig.SOUND_BASE_URL +
				track.getProduct().getCode() + "/" + track.getSampleFilename());
		out.println("\n ...PLAYING...TRACK..." + track.getTitle() + "...");
		out.println("\n---------------------------------------------------");
	}

	public static void displayUserInfo(User u, PrintStream out) {
		out.println("\n---------------------------------------------------");
		out.println("\n USER INFORMATION \n");
		out.println("\n Name:" + u.getFirstname() + " " + u.getLastname());
		out.println("\n Email Addr : " + u.getEmailAddress());
		out.println("\n---------------------------------------------------");
	}
	
	// changed print to println, so prompts show under ant execution
	public static String readEntry(Scanner in, String prompt)
			throws IOException {
		System.out.println(prompt + ":");
		return  in.nextLine().trim();
	}

}
