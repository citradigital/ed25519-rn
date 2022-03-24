package digital.citra.ed25519rn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Random;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.DecoderException;

public final class KeyPair {
  public final PrivateKey privateKey;
  public final PublicKey publicKey;


  public KeyPair() throws IllegalDataSizeException {
    Random r = new SecureRandom();
    byte[] priv = new byte[32];
    r.nextBytes(priv);

    byte[] pubKey = new byte[32];
    byte[] privSignature = new byte[32];
    Curve.keygen(pubKey, privSignature, priv);
    privateKey = new PrivateKey(priv, privSignature);
    publicKey = new PublicKey(pubKey);
  }

  public KeyPair(final PrivateKey privateKey, final PublicKey publicKey) {
    this.privateKey = privateKey;
    this.publicKey = publicKey;
  }

  public byte[] encode() throws IOException {
    ByteArrayOutputStream ss = new ByteArrayOutputStream();
    ss.write(privateKey.encode());
    ss.write(publicKey.encode());
    return ss.toByteArray();
  }

  public final String hexString() throws IOException {
    return Hex.encodeHexString(encode());
  }

  public static KeyPair decode(final byte[] raw) throws InvalidKeyException, IOException, IllegalDataSizeException {
    PrivateKey privateKey = PrivateKey.decode(raw, 0);
    PublicKey publicKey = PublicKey.decode(raw, 64);
    return new KeyPair(privateKey, publicKey);
  }

  public static KeyPair fromHex(final String hex) throws InvalidKeyException, IOException, IllegalDataSizeException, DecoderException {
    final byte[] raw = Hex.decodeHex(hex);
    PrivateKey privateKey = PrivateKey.decode(raw, 0);
    PublicKey publicKey = PublicKey.decode(raw, 64);
    return new KeyPair(privateKey, publicKey);
  }

  @Override
  public boolean equals(Object other) {
    return ((KeyPair)other).privateKey.equals(privateKey) &&
        ((KeyPair)other).publicKey.equals(publicKey);
  }
}
