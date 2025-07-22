package com.signify.hue.flutterreactiveble.ble

import android.os.ParcelUuid
import com.polidea.rxandroidble2.RxBleDeviceServices
import com.signify.hue.flutterreactiveble.model.ScanMode
import com.signify.hue.flutterreactiveble.utils.Duration
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.util.UUID

public interface BleClient {
   public val connectionUpdateSubject: BehaviorSubject<ConnectionUpdate>

   public abstract fun clearGattCache(deviceId: String): Completable {
   }

   public abstract fun connectToDevice(deviceId: String, timeout: Duration) {
   }

   public abstract fun disconnectAllDevices() {
   }

   public abstract fun disconnectDevice(deviceId: String) {
   }

   public abstract fun discoverServices(deviceId: String): Single<RxBleDeviceServices> {
   }

   public abstract fun initializeClient() {
   }

   public abstract fun negotiateMtuSize(deviceId: String, size: Int): Single<MtuNegotiateResult> {
   }

   public abstract fun observeBleStatus(): Observable<BleStatus> {
   }

   public abstract fun readCharacteristic(deviceId: String, characteristicId: UUID, characteristicInstanceId: Int): Single<CharOperationResult> {
   }

   public abstract fun readRssi(deviceId: String): Single<Int> {
   }

   public abstract fun requestConnectionPriority(deviceId: String, priority: ConnectionPriority): Single<RequestConnectionPriorityResult> {
   }

   public abstract fun scanForDevices(services: List<ParcelUuid>, scanMode: ScanMode, requireLocationServicesEnabled: Boolean): Observable<ScanInfo> {
   }

   public abstract fun setupNotification(deviceId: String, characteristicId: UUID, characteristicInstanceId: Int): Observable<ByteArray> {
   }

   public abstract fun writeCharacteristicWithResponse(deviceId: String, characteristicId: UUID, characteristicInstanceId: Int, value: ByteArray): Single<
         CharOperationResult
      > {
   }

   public abstract fun writeCharacteristicWithoutResponse(deviceId: String, characteristicId: UUID, characteristicInstanceId: Int, value: ByteArray): Single<
         CharOperationResult
      > {
   }
}
