import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import {
  generateKeypair,
  getPublicKey,
  getSharedKey,
  sign,
  verify,
} from 'ed25519-rn';

import hex from 'string-hex';

export default function App() {
  React.useEffect(() => {
    generateKeypair().then(async (kp) => {
      const pk = await getPublicKey(kp);

      console.log('kp', kp);
      console.log('pk', await getPublicKey(kp));

      const kp2 = await generateKeypair();
      const pk2 = await getPublicKey(kp2);

      const sk1 = await getSharedKey(kp, pk2);
      const sk2 = await getSharedKey(kp2, pk);

      console.log('sk1', sk1);
      console.log('sk2', sk2);
      console.log('sk1 == sk2', sk1 === sk2);

      const data = 'omama olala osama obama orama oyama owawa';
      const signature = await sign(kp, hex(data));
      console.log('sig verification', await verify(pk, hex(data), signature));

    });
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: OK</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
