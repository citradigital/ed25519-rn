# ed25519-rn

React Native ed25519 module

## Installation

```sh
npm install ed25519-rn
```

## Usage

```js
import { multiply } from "ed25519-rn";

// ...

      const kp = await generateKeyPair(kp);
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

// should output something like this

[Thu Mar 24 2022 07:15:41.459]  LOG      Running "Ed25519RnExample" with {"rootTag":81}
[Thu Mar 24 2022 07:15:41.472]  LOG      kp 90ac8b6af5f8f915088f1e8bb649c0c595ad3977734d98e36b75647641c32e584988fde505f4510bf96170a2089c7fbc831d38335ca6bd3ee5a38ac5827d7c0fef4eef82b3ec52d1d723b550d6cc7b3f7e07728e31c6c2be81bc62849b789739
[Thu Mar 24 2022 07:15:41.474]  LOG      pk ef4eef82b3ec52d1d723b550d6cc7b3f7e07728e31c6c2be81bc62849b789739
[Thu Mar 24 2022 07:15:41.499]  LOG      sk1 f8194f6768896fbb46ae1f9698e98c0dbc3c02c0c62c7d7744f75538a96c685f
[Thu Mar 24 2022 07:15:41.500]  LOG      sk2 f8194f6768896fbb46ae1f9698e98c0dbc3c02c0c62c7d7744f75538a96c685f
[Thu Mar 24 2022 07:15:41.501]  LOG      sk1 == sk2 true
[Thu Mar 24 2022 07:15:41.508]  LOG      sig verification true

```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

(c) 2022 PT Citra Digital Lintas https://citra.digital
