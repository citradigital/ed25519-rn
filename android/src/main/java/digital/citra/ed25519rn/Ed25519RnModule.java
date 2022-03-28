package digital.citra.ed25519rn;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.Curve25519KeyPair;


@ReactModule(name = Ed25519RnModule.NAME)
public class Ed25519RnModule extends ReactContextBaseJavaModule {
  public static final String NAME = "Ed25519Rn";

  public Ed25519RnModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void generateKeypair(Promise promise) throws IOException {
    Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);

    Curve25519KeyPair keyPair = Curve25519.getInstance(Curve25519.BEST).generateKeyPair();

    promise.resolve(Hex.encodeHexString(keyPair.getPrivateKey()) + Hex.encodeHexString(keyPair.getPublicKey()));
  }

  @ReactMethod
  public void getPublicKey(String keyPair, Promise promise)
      throws InvalidKeyException, IOException, DecoderException {
    Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);

    promise.resolve(keyPair.substring(64));
  }

  @ReactMethod
  public void getSharedKey(String keyPair, String publicKey, Promise promise)
      throws InvalidKeyException, IOException, DecoderException {

    Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);
    byte[] privateKey = Hex.decodeHex(keyPair.substring(0, 64));
    byte[] publicKeyRaw = Hex.decodeHex(publicKey);

    byte[] sharedSecret = cipher.calculateAgreement(publicKeyRaw, privateKey);

    promise.resolve(Hex.encodeHexString(sharedSecret));
  }

  @ReactMethod
  public void sign(String keyPair, String data, Promise promise) throws InvalidKeyException, SignatureException,
      IOException, DecoderException, NoSuchAlgorithmException {

    Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);

    byte[] privateKey = Hex.decodeHex(keyPair.substring(0, 64));
    byte[] signature = cipher.calculateSignature(privateKey, Hex.decodeHex(data));

    promise.resolve(Hex.encodeHexString(signature));
  }

  @ReactMethod
  public void verify(String publicKey, String data, String signature, Promise promise) throws SignatureException,
      InvalidKeyException, IOException, DecoderException, NoSuchAlgorithmException {

    byte[] publicKeyRaw = Hex.decodeHex(publicKey);

    Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);
    boolean validSignature = cipher.verifySignature(publicKeyRaw, Hex.decodeHex(data), Hex.decodeHex(signature));

    promise.resolve(validSignature);
  }

}
