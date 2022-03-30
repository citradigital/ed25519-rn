import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'ed25519-rn' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const Ed25519Rn = NativeModules.Ed25519Rn
  ? NativeModules.Ed25519Rn
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

function generateKeypair(): Promise<string> {
  return Ed25519Rn.generateKeypair();
}

function getPublicKey(keyPair: string): Promise<string> {
  return Ed25519Rn.getPublicKey(keyPair);
}

function getSharedKey(
  keyPair: string,
  otherPublicKey: string
): Promise<string> {
  return Ed25519Rn.getSharedKey(keyPair, otherPublicKey);
}

function sign(keyPair: string, data: string): Promise<string> {
  return Ed25519Rn.sign(keyPair, data);
}

function verify(publicKey: string, data: string, signature: string): Promise<boolean> {
  return Ed25519Rn.verify(publicKey, data, signature);
}

export default {
  verify,
  generateKeypair,
  getPublicKey,
  getSharedKey,
  sign
};