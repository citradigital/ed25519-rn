import {generateKeypair, getPublicKey, getSharedKey} from "ed25519-rn"

test('GenerateKeypair', async () => {
  const keyPair = await generateKeypair()
  expect(keyPair).toHaveLength(128)
})

test('GetPublicKey', async () => {
  const keyPair = '686c79545664484f6e3243765037456347716c506674547330693552624243747cc5c3fc16378d586d82564d4dfc3acde12a8d280eddafb3e42eb63ac4e7a542'
  const publicKey = await getPublicKey(keyPair)
  expect(publicKey).toHaveLength(64)
})

test('GetSharedKey', async () => {
  const userAKeypair = '50703473474a4936396673626f545866477254674965514230455438544676637617fc80c3ab734932a47886510a94162ad9d96c26da78e4504599b535fe5b4b'
  const userAPublicKey = '7617fc80c3ab734932a47886510a94162ad9d96c26da78e4504599b535fe5b4b'
  const userBKeypair  = '686c79545664484f6e3243765037456347716c506674547330693552624243747cc5c3fc16378d586d82564d4dfc3acde12a8d280eddafb3e42eb63ac4e7a542'
  const userBPublicKey = '7cc5c3fc16378d586d82564d4dfc3acde12a8d280eddafb3e42eb63ac4e7a542'
  const sharedKey1 = await getSharedKey(userAKeypair, userBPublicKey)
  const sharedKey2 = await getSharedKey(userBKeypair, userAPublicKey)
  expect(sharedKey1).toHaveLength(64)
  expect(sharedKey2).toHaveLength(64)
  expect(sharedKey1).toStrictEqual(sharedKey2)
})
