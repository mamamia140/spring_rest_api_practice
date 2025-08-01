package tr.gov.bilgem.restpractice.device.watcher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tr.gov.bilgem.restpractice.device.DeviceService;
import tr.gov.bilgem.restpractice.model.Device;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;

/**
 * Task to monitor a specific device's connectivity status
 *
 * @author Serdar Serpen
 * @date Nov 8, 2023
 * @since 1.0.0
 */
@Getter
@Setter
@RequiredArgsConstructor
public class DeviceWatcherTask implements Runnable {

	private static final Log logger = LogFactory.getLog(DeviceWatcherTask.class);

	private final Device device;

	private final DeviceService deviceService;

	private final int timeoutMs;

	@Override
	public void run() {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Running device watcher task for device: " + device.getName() +
						" (" + device.getIpAddress() + ")");
			}

			boolean isAccessible = pingDevice(device.getIpAddress());

			// Update device accessibility status if it has changed
			if (device.getAccessible() == null || device.getAccessible() != isAccessible) {
				updateDeviceAccessibility(isAccessible);

				if (logger.isInfoEnabled()) {
					logger.info("Device " + device.getName() + " (" + device.getIpAddress() +
							") accessibility changed to: " + isAccessible);
				}
			}

		} catch (Exception e) {
			logger.error("Error while checking device accessibility for " + device.getName() +
					" (" + device.getIpAddress() + ")", e);

			// Set device as inaccessible on error
			if (device.getAccessible() == null || device.getAccessible()) {
				updateDeviceAccessibility(false);
			}
		}
	}

	private boolean pingDevice(String ipAddress) {
		try {
			InetAddress inet = InetAddress.getByName(ipAddress);
			return inet.isReachable(timeoutMs);
		} catch (IOException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Ping failed for " + ipAddress + ": " + e.getMessage());
			}
			return false;
		}
	}

	private void updateDeviceAccessibility(boolean accessible) {
		try {
			// Refresh device from database to get latest state
			Optional<Device> currentDevice = deviceService.findById(device.getId());
			if (currentDevice.isPresent()) {
				Device deviceToUpdate = currentDevice.get();
				deviceToUpdate.setAccessible(accessible);
				deviceService.update(deviceToUpdate);
			} else {
				logger.warn("Device with ID " + device.getId() + " not found in database");
			}
		} catch (Exception e) {
			logger.error("Failed to update accessibility status for device " + device.getName(), e);
		}
	}
}