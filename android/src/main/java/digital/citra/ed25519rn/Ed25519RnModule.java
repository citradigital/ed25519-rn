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
    public void generateKeypair(Promise promise) throws IllegalDataSizeException, IOException {
      promise.resolve((new digital.citra.ed25519rn.KeyPair()).hexString());
    }

    @ReactMethod
    public void getPublicKey(String keyPair, Promise promise) throws InvalidKeyException, IllegalDataSizeException, IOException, DecoderException {
      final digital.citra.ed25519rn.KeyPair pair =  digital.citra.ed25519rn.KeyPair.fromHex(keyPair);
      promise.resolve(pair.publicKey.hexString());
    }


    @ReactMethod
    public void getSharedKey(String keyPair, String publicKey, Promise promise) throws InvalidKeyException, IllegalDataSizeException, IOException, DecoderException {
      final digital.citra.ed25519rn.KeyPair pair =  digital.citra.ed25519rn.KeyPair.fromHex(keyPair);
      final digital.citra.ed25519rn.PublicKey pubKey = digital.citra.ed25519rn.PublicKey.fromHex(publicKey);
      final byte[] shared = pair.privateKey.shareSecret(pubKey);

      promise.resolve(Hex.encodeHexString(shared));
    }

    @ReactMethod
    public void sign(String keyPair, String data, Promise promise) throws InvalidKeyException, SignatureException, IllegalDataSizeException, IOException, DecoderException, NoSuchAlgorithmException {
      final byte[] rawData = Hex.decodeHex(data);

      final digital.citra.ed25519rn.KeyPair pair =  digital.citra.ed25519rn.KeyPair.fromHex(keyPair);
      final digital.citra.ed25519rn.Signature signature = pair.privateKey.sign(rawData);

      final byte[] rawSignature = signature.getBytes();

      promise.resolve(Hex.encodeHexString(rawSignature));
    }

    @ReactMethod
    public void verify(String publicKey, String data, String signature, Promise promise) throws SignatureException, InvalidKeyException, IllegalDataSizeException, IOException, DecoderException, NoSuchAlgorithmException {
      final byte[] rawData = Hex.decodeHex(data);
      final byte[] rawSignature = Hex.decodeHex(signature);

      final digital.citra.ed25519rn.PublicKey publicKeyObj = digital.citra.ed25519rn.PublicKey.fromHex(publicKey);

      promise.resolve(publicKeyObj.verify(rawData, new digital.citra.ed25519rn.Signature(rawSignature)));
    }


}
