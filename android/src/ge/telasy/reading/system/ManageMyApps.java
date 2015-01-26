package ge.telasy.reading.system;

import android.app.Activity;

public class ManageMyApps {
	public static Activity TARSAktivity;
	public static Activity AdminlayoutActivity;
	public static Activity routedownload;
	public static Activity routemanager;
	public static Activity UserManager;
	public static Activity login;
	public static Activity enterCountersShow;
	public static Activity outOfReester;
	public static Activity FromQuery;

	public static void close() {
		if (ManageMyApps.AdminlayoutActivity != null) {
			ManageMyApps.AdminlayoutActivity.finish();
		}
		if (ManageMyApps.routedownload != null) {
			ManageMyApps.routedownload.finish();
		}
		if (ManageMyApps.routemanager != null) {
			ManageMyApps.routemanager.finish();
		}
		if (ManageMyApps.UserManager != null) {
			ManageMyApps.UserManager.finish();
		}
		if (ManageMyApps.login != null) {
			ManageMyApps.login.finish();
		}
		if (ManageMyApps.enterCountersShow != null) {
			ManageMyApps.enterCountersShow.finish();
		}
		if (ManageMyApps.outOfReester != null) {
			ManageMyApps.outOfReester.finish();
		}
		if (ManageMyApps.FromQuery != null) {
			ManageMyApps.FromQuery.finish();
		}
		if (ManageMyApps.TARSAktivity != null) {
			ManageMyApps.TARSAktivity.finish();
		}
	}

}
