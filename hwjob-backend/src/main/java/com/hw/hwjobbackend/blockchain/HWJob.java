package com.hw.hwjobbackend.blockchain;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.CustomError;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class HWJob extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50336040518060400160405280600f81526020017f467265656c616e636520506f696e7400000000000000000000000000000000008152506040518060400160405280600381526020017f464c500000000000000000000000000000000000000000000000000000000000815250816003908161008d919061043d565b50806004908161009d919061043d565b505050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16036101125760006040517f1e4fbdf70000000000000000000000000000000000000000000000000000000081526004016101099190610550565b60405180910390fd5b6101218161012760201b60201c565b5061056b565b6000600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905081600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b600081519050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061026e57607f821691505b60208210810361028157610280610227565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026102e97fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff826102ac565b6102f386836102ac565b95508019841693508086168417925050509392505050565b6000819050919050565b6000819050919050565b600061033a6103356103308461030b565b610315565b61030b565b9050919050565b6000819050919050565b6103548361031f565b61036861036082610341565b8484546102b9565b825550505050565b600090565b61037d610370565b61038881848461034b565b505050565b5b818110156103ac576103a1600082610375565b60018101905061038e565b5050565b601f8211156103f1576103c281610287565b6103cb8461029c565b810160208510156103da578190505b6103ee6103e68561029c565b83018261038d565b50505b505050565b600082821c905092915050565b6000610414600019846008026103f6565b1980831691505092915050565b600061042d8383610403565b9150826002028217905092915050565b610446826101ed565b67ffffffffffffffff81111561045f5761045e6101f8565b5b6104698254610256565b6104748282856103b0565b600060209050601f8311600181146104a75760008415610495578287015190505b61049f8582610421565b865550610507565b601f1984166104b586610287565b60005b828110156104dd578489015182556001820191506020850194506020810190506104b8565b868310156104fa57848901516104f6601f891682610403565b8355505b6001600288020188555050505b505050505050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061053a8261050f565b9050919050565b61054a8161052f565b82525050565b60006020820190506105656000830184610541565b92915050565b6124368061057a6000396000f3fe608060405234801561001057600080fd5b506004361061018e5760003560e01c80637b057258116100de578063a9059cbb11610097578063c3c5a54711610071578063c3c5a54714610485578063dd62ed3e146104b5578063e11e1b0c146104e5578063f2fde38b146105035761018e565b8063a9059cbb14610409578063b9f7945114610439578063c1708e08146104695761018e565b80637b057258146103335780638ce6b3cd146103515780638da5cb5b1461036d57806395d89b411461038b5780639ae697bf146103a95780639c89a0e2146103d95761018e565b806323b872dd1161014b5780634d545a5e116101255780634d545a5e146102c15780636a0383cb146102dd57806370a08231146102f9578063715018a6146103295761018e565b806323b872dd14610255578063313ce5671461028557806339a488e4146102a35761018e565b806306fdde0314610193578063095ea7b3146101b157806313f1fc33146101e157806318160ddd146101fd5780631df829e11461021b5780632199d5cd14610239575b600080fd5b61019b61051f565b6040516101a89190611c99565b60405180910390f35b6101cb60048036038101906101c69190611d54565b6105b1565b6040516101d89190611daf565b60405180910390f35b6101fb60048036038101906101f69190611d54565b6105d4565b005b6102056106c4565b6040516102129190611dd9565b60405180910390f35b6102236106ce565b6040516102309190611dd9565b60405180910390f35b610253600480360381019061024e9190611df4565b6106d3565b005b61026f600480360381019061026a9190611e21565b6108a4565b60405161027c9190611daf565b60405180910390f35b61028d6108d3565b60405161029a9190611e90565b60405180910390f35b6102ab6108dc565b6040516102b89190611dd9565b60405180910390f35b6102db60048036038101906102d69190611d54565b6108e1565b005b6102f760048036038101906102f29190611e21565b610a73565b005b610313600480360381019061030e9190611df4565b610d7d565b6040516103209190611dd9565b60405180910390f35b610331610dc5565b005b61033b610dd9565b6040516103489190611dd9565b60405180910390f35b61036b60048036038101906103669190611ed7565b610dde565b005b610375611094565b6040516103829190611f4d565b60405180910390f35b6103936110be565b6040516103a09190611c99565b60405180910390f35b6103c360048036038101906103be9190611df4565b611150565b6040516103d09190611dd9565b60405180910390f35b6103f360048036038101906103ee9190611df4565b611168565b6040516104009190611dd9565b60405180910390f35b610423600480360381019061041e9190611d54565b6111b1565b6040516104309190611daf565b60405180910390f35b610453600480360381019061044e9190611df4565b6111d4565b6040516104609190611dd9565b60405180910390f35b610483600480360381019061047e9190611d54565b6111ec565b005b61049f600480360381019061049a9190611df4565b6112dc565b6040516104ac9190611daf565b60405180910390f35b6104cf60048036038101906104ca9190611f68565b6112fc565b6040516104dc9190611dd9565b60405180910390f35b6104ed611383565b6040516104fa9190611dd9565b60405180910390f35b61051d60048036038101906105189190611df4565b611393565b005b60606003805461052e90611fd7565b80601f016020809104026020016040519081016040528092919081815260200182805461055a90611fd7565b80156105a75780601f1061057c576101008083540402835291602001916105a7565b820191906000526020600020905b81548152906001019060200180831161058a57829003601f168201915b5050505050905090565b6000806105bc611419565b90506105c9818585611421565b600191505092915050565b6105dc611433565b600860008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16610668576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161065f90612054565b60405180910390fd5b61067282826114ba565b8173ffffffffffffffffffffffffffffffffffffffff167fbf3f4de5b12c56d34953f87530677935ddbe009217dd8aa33b352fe82a4f12e2826040516106b89190611dd9565b60405180910390a25050565b6000600254905090565b600581565b6106db611433565b600860008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1615610768576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161075f906120c0565b60405180910390fd5b61077381600061153c565b6050600760008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055506001600860008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff0219169083151502179055508073ffffffffffffffffffffffffffffffffffffffff167e0143ba88ded9808e3f60de522fa585f0874534e7a2fe23b106311a77577f5560506040516108569190611dd9565b60405180910390a28073ffffffffffffffffffffffffffffffffffffffff167f54db7a5cb4735e1aac1f53db512d3390390bb6637bd30ad4bf9fc98667d9b9b960405160405180910390a250565b6000806108af611419565b90506108bc8582856115be565b6108c7858585611653565b60019150509392505050565b60006012905090565b600581565b6108e9611433565b600860008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16610975576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161096c9061212c565b60405180910390fd5b8061097f83610d7d565b10156109c0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109b790612198565b60405180910390fd5b6109cb823083611653565b80600660008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254610a1a91906121e7565b925050819055508173ffffffffffffffffffffffffffffffffffffffff167f9f1ec8c880f76798e7b793325d625e9b60e4082a553c98f42b6cda368dd6000882604051610a679190611dd9565b60405180910390a25050565b610a7b611433565b600860008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16610b07576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610afe90612267565b60405180910390fd5b80600660008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015610b89576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b80906122d3565b60405180910390fd5b80600660008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254610bd891906122f3565b92505081905550610bea308483611653565b600060646005600760008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054610c3a9190612327565b610c449190612398565b905080600760008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254610c9591906122f3565b925050819055508273ffffffffffffffffffffffffffffffffffffffff167fecdb2639c9ed58b2f66ce690bdd597b285cbc8e9de572243c483988e00122640600760008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054604051610d219190611dd9565b60405180910390a28373ffffffffffffffffffffffffffffffffffffffff167fd7dee2702d63ad89917b6a4da9981c90c4d24f8c2bdfd64c604ecae57d8d065183604051610d6f9190611dd9565b60405180910390a250505050565b60008060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b610dcd611433565b610dd76000611747565b565b605081565b610de6611433565b600860008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16610e72576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610e6990612267565b60405180910390fd5b81600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015610ef4576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610eeb906122d3565b60405180910390fd5b81600660008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254610f4391906122f3565b92505081905550610f55308484611653565b8015611040576005600760008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254610fab91906121e7565b925050819055508273ffffffffffffffffffffffffffffffffffffffff167ffac498e7ee7d43e382528bf22afd838a7c6abfa5f54e7c564f7e5a54a18ca34c600760008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546040516110379190611dd9565b60405180910390a25b8273ffffffffffffffffffffffffffffffffffffffff167fb21fb52d5749b80f3182f8c6992236b5e5576681880914484d7f4c9b062e619e836040516110869190611dd9565b60405180910390a250505050565b6000600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b6060600480546110cd90611fd7565b80601f01602080910402602001604051908101604052809291908181526020018280546110f990611fd7565b80156111465780601f1061111b57610100808354040283529160200191611146565b820191906000526020600020905b81548152906001019060200180831161112957829003601f168201915b5050505050905090565b60066020528060005260406000206000915090505481565b6000600760008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050919050565b6000806111bc611419565b90506111c9818585611653565b600191505092915050565b60076020528060005260406000206000915090505481565b6111f4611433565b600860008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16611280576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161127790612054565b60405180910390fd5b61128a828261153c565b8173ffffffffffffffffffffffffffffffffffffffff167f8dd5dbc440d9967f696981d49243f2e8d65f7ff56dc2beb03b76ccd367fd1d73826040516112d09190611dd9565b60405180910390a25050565b60086020528060005260406000206000915054906101000a900460ff1681565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905092915050565b600061138e30610d7d565b905090565b61139b611433565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff160361140d5760006040517f1e4fbdf70000000000000000000000000000000000000000000000000000000081526004016114049190611f4d565b60405180910390fd5b61141681611747565b50565b600033905090565b61142e838383600161180d565b505050565b61143b611419565b73ffffffffffffffffffffffffffffffffffffffff16611459611094565b73ffffffffffffffffffffffffffffffffffffffff16146114b85761147c611419565b6040517f118cdaa70000000000000000000000000000000000000000000000000000000081526004016114af9190611f4d565b60405180910390fd5b565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff160361152c5760006040517f96c6fd1e0000000000000000000000000000000000000000000000000000000081526004016115239190611f4d565b60405180910390fd5b611538826000836119e4565b5050565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16036115ae5760006040517fec442f050000000000000000000000000000000000000000000000000000000081526004016115a59190611f4d565b60405180910390fd5b6115ba600083836119e4565b5050565b60006115ca84846112fc565b90507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff81101561164d578181101561163d578281836040517ffb8f41b2000000000000000000000000000000000000000000000000000000008152600401611634939291906123c9565b60405180910390fd5b61164c8484848403600061180d565b5b50505050565b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16036116c55760006040517f96c6fd1e0000000000000000000000000000000000000000000000000000000081526004016116bc9190611f4d565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16036117375760006040517fec442f0500000000000000000000000000000000000000000000000000000000815260040161172e9190611f4d565b60405180910390fd5b6117428383836119e4565b505050565b6000600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905081600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508173ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a35050565b600073ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff160361187f5760006040517fe602df050000000000000000000000000000000000000000000000000000000081526004016118769190611f4d565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff16036118f15760006040517f94280d620000000000000000000000000000000000000000000000000000000081526004016118e89190611f4d565b60405180910390fd5b81600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190555080156119de578273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040516119d59190611dd9565b60405180910390a35b50505050565b600073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff1603611a36578060026000828254611a2a91906121e7565b92505081905550611b09565b60008060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905081811015611ac2578381836040517fe450d38c000000000000000000000000000000000000000000000000000000008152600401611ab9939291906123c9565b60405180910390fd5b8181036000808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550505b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1603611b525780600260008282540392505081905550611b9f565b806000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055505b8173ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef83604051611bfc9190611dd9565b60405180910390a3505050565b600081519050919050565b600082825260208201905092915050565b60005b83811015611c43578082015181840152602081019050611c28565b60008484015250505050565b6000601f19601f8301169050919050565b6000611c6b82611c09565b611c758185611c14565b9350611c85818560208601611c25565b611c8e81611c4f565b840191505092915050565b60006020820190508181036000830152611cb38184611c60565b905092915050565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000611ceb82611cc0565b9050919050565b611cfb81611ce0565b8114611d0657600080fd5b50565b600081359050611d1881611cf2565b92915050565b6000819050919050565b611d3181611d1e565b8114611d3c57600080fd5b50565b600081359050611d4e81611d28565b92915050565b60008060408385031215611d6b57611d6a611cbb565b5b6000611d7985828601611d09565b9250506020611d8a85828601611d3f565b9150509250929050565b60008115159050919050565b611da981611d94565b82525050565b6000602082019050611dc46000830184611da0565b92915050565b611dd381611d1e565b82525050565b6000602082019050611dee6000830184611dca565b92915050565b600060208284031215611e0a57611e09611cbb565b5b6000611e1884828501611d09565b91505092915050565b600080600060608486031215611e3a57611e39611cbb565b5b6000611e4886828701611d09565b9350506020611e5986828701611d09565b9250506040611e6a86828701611d3f565b9150509250925092565b600060ff82169050919050565b611e8a81611e74565b82525050565b6000602082019050611ea56000830184611e81565b92915050565b611eb481611d94565b8114611ebf57600080fd5b50565b600081359050611ed181611eab565b92915050565b60008060008060808587031215611ef157611ef0611cbb565b5b6000611eff87828801611d09565b9450506020611f1087828801611d09565b9350506040611f2187828801611d3f565b9250506060611f3287828801611ec2565b91505092959194509250565b611f4781611ce0565b82525050565b6000602082019050611f626000830184611f3e565b92915050565b60008060408385031215611f7f57611f7e611cbb565b5b6000611f8d85828601611d09565b9250506020611f9e85828601611d09565b9150509250929050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680611fef57607f821691505b60208210810361200257612001611fa8565b5b50919050565b7f5573657220636875612064616e67206b79000000000000000000000000000000600082015250565b600061203e601183611c14565b915061204982612008565b602082019050919050565b6000602082019050818103600083015261206d81612031565b9050919050565b7f557365722064612064616e67206b790000000000000000000000000000000000600082015250565b60006120aa600f83611c14565b91506120b582612074565b602082019050919050565b600060208201905081810360008301526120d98161209d565b9050919050565b7f52656372756974657220636875612064616e67206b7900000000000000000000600082015250565b6000612116601683611c14565b9150612121826120e0565b602082019050919050565b6000602082019050818103600083015261214581612109565b9050919050565b7f4b686f6e67206475206469656d00000000000000000000000000000000000000600082015250565b6000612182600d83611c14565b915061218d8261214c565b602082019050919050565b600060208201905081810360008301526121b181612175565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006121f282611d1e565b91506121fd83611d1e565b9250828201905080821115612215576122146121b8565b5b92915050565b7f467265656c616e63657220636875612064616e67206b79000000000000000000600082015250565b6000612251601783611c14565b915061225c8261221b565b602082019050919050565b6000602082019050818103600083015261228081612244565b9050919050565b7f4b686f6e67206475206469656d206c6f636b0000000000000000000000000000600082015250565b60006122bd601283611c14565b91506122c882612287565b602082019050919050565b600060208201905081810360008301526122ec816122b0565b9050919050565b60006122fe82611d1e565b915061230983611d1e565b9250828203905081811115612321576123206121b8565b5b92915050565b600061233282611d1e565b915061233d83611d1e565b925082820261234b81611d1e565b91508282048414831517612362576123616121b8565b5b5092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601260045260246000fd5b60006123a382611d1e565b91506123ae83611d1e565b9250826123be576123bd612369565b5b828204905092915050565b60006060820190506123de6000830186611f3e565b6123eb6020830185611dca565b6123f86040830184611dca565b94935050505056fea26469706673582212208ec556e59586c66ce802cc4434e902454a936fd402fd6f925682909ec125ca7b64736f6c634300081c0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_GOOD_JOB_REWARD = "GOOD_JOB_REWARD";

    public static final String FUNC_INITIAL_REPUTATION = "INITIAL_REPUTATION";

    public static final String FUNC_VIOLATION_PENALTY = "VIOLATION_PENALTY";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURNPOINT = "burnPoint";

    public static final String FUNC_COMPLETEJOB = "completeJob";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_ESCROWBALANCE = "escrowBalance";

    public static final String FUNC_FAILJOB = "failJob";

    public static final String FUNC_GETREPUTATION = "getReputation";

    public static final String FUNC_ISREGISTERED = "isRegistered";

    public static final String FUNC_LOCKFORJOBFORRECRUITER = "lockForJobForRecruiter";

    public static final String FUNC_LOCKEDBALANCE = "lockedBalance";

    public static final String FUNC_MINTPOINT = "mintPoint";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REGISTERUSER = "registerUser";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_REPUTATION = "reputation";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final CustomError ERC20INSUFFICIENTALLOWANCE_ERROR = new CustomError("ERC20InsufficientAllowance", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final CustomError ERC20INSUFFICIENTBALANCE_ERROR = new CustomError("ERC20InsufficientBalance", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final CustomError ERC20INVALIDAPPROVER_ERROR = new CustomError("ERC20InvalidApprover", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC20INVALIDRECEIVER_ERROR = new CustomError("ERC20InvalidReceiver", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC20INVALIDSENDER_ERROR = new CustomError("ERC20InvalidSender", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC20INVALIDSPENDER_ERROR = new CustomError("ERC20InvalidSpender", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError OWNABLEINVALIDOWNER_ERROR = new CustomError("OwnableInvalidOwner", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError OWNABLEUNAUTHORIZEDACCOUNT_ERROR = new CustomError("OwnableUnauthorizedAccount", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOCKED_EVENT = new Event("Locked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event POINTBURNED_EVENT = new Event("PointBurned", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event POINTMINTED_EVENT = new Event("PointMinted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REFUNDED_EVENT = new Event("Refunded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event RELEASED_EVENT = new Event("Released", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REPUTATIONDECREASED_EVENT = new Event("ReputationDecreased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REPUTATIONINCREASED_EVENT = new Event("ReputationIncreased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event REPUTATIONINITIALIZED_EVENT = new Event("ReputationInitialized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event USERREGISTERED_EVENT = new Event("UserRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected HWJob(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HWJob(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HWJob(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HWJob(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ApprovalEventResponse> getApprovalEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
        ApprovalEventResponse typedResponse = new ApprovalEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public static List<LockedEventResponse> getLockedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOCKED_EVENT, transactionReceipt);
        ArrayList<LockedEventResponse> responses = new ArrayList<LockedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LockedEventResponse typedResponse = new LockedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recruiter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LockedEventResponse getLockedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOCKED_EVENT, log);
        LockedEventResponse typedResponse = new LockedEventResponse();
        typedResponse.log = log;
        typedResponse.recruiter = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<LockedEventResponse> lockedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLockedEventFromLog(log));
    }

    public Flowable<LockedEventResponse> lockedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOCKED_EVENT));
        return lockedEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public static List<PointBurnedEventResponse> getPointBurnedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(POINTBURNED_EVENT, transactionReceipt);
        ArrayList<PointBurnedEventResponse> responses = new ArrayList<PointBurnedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PointBurnedEventResponse typedResponse = new PointBurnedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PointBurnedEventResponse getPointBurnedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(POINTBURNED_EVENT, log);
        PointBurnedEventResponse typedResponse = new PointBurnedEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<PointBurnedEventResponse> pointBurnedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPointBurnedEventFromLog(log));
    }

    public Flowable<PointBurnedEventResponse> pointBurnedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(POINTBURNED_EVENT));
        return pointBurnedEventFlowable(filter);
    }

    public static List<PointMintedEventResponse> getPointMintedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(POINTMINTED_EVENT, transactionReceipt);
        ArrayList<PointMintedEventResponse> responses = new ArrayList<PointMintedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PointMintedEventResponse typedResponse = new PointMintedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PointMintedEventResponse getPointMintedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(POINTMINTED_EVENT, log);
        PointMintedEventResponse typedResponse = new PointMintedEventResponse();
        typedResponse.log = log;
        typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<PointMintedEventResponse> pointMintedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPointMintedEventFromLog(log));
    }

    public Flowable<PointMintedEventResponse> pointMintedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(POINTMINTED_EVENT));
        return pointMintedEventFlowable(filter);
    }

    public static List<RefundedEventResponse> getRefundedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(REFUNDED_EVENT, transactionReceipt);
        ArrayList<RefundedEventResponse> responses = new ArrayList<RefundedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RefundedEventResponse typedResponse = new RefundedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recruiter = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RefundedEventResponse getRefundedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(REFUNDED_EVENT, log);
        RefundedEventResponse typedResponse = new RefundedEventResponse();
        typedResponse.log = log;
        typedResponse.recruiter = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<RefundedEventResponse> refundedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRefundedEventFromLog(log));
    }

    public Flowable<RefundedEventResponse> refundedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REFUNDED_EVENT));
        return refundedEventFlowable(filter);
    }

    public static List<ReleasedEventResponse> getReleasedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(RELEASED_EVENT, transactionReceipt);
        ArrayList<ReleasedEventResponse> responses = new ArrayList<ReleasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReleasedEventResponse typedResponse = new ReleasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.freelancer = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ReleasedEventResponse getReleasedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(RELEASED_EVENT, log);
        ReleasedEventResponse typedResponse = new ReleasedEventResponse();
        typedResponse.log = log;
        typedResponse.freelancer = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ReleasedEventResponse> releasedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getReleasedEventFromLog(log));
    }

    public Flowable<ReleasedEventResponse> releasedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RELEASED_EVENT));
        return releasedEventFlowable(filter);
    }

    public static List<ReputationDecreasedEventResponse> getReputationDecreasedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(REPUTATIONDECREASED_EVENT, transactionReceipt);
        ArrayList<ReputationDecreasedEventResponse> responses = new ArrayList<ReputationDecreasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReputationDecreasedEventResponse typedResponse = new ReputationDecreasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ReputationDecreasedEventResponse getReputationDecreasedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(REPUTATIONDECREASED_EVENT, log);
        ReputationDecreasedEventResponse typedResponse = new ReputationDecreasedEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ReputationDecreasedEventResponse> reputationDecreasedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getReputationDecreasedEventFromLog(log));
    }

    public Flowable<ReputationDecreasedEventResponse> reputationDecreasedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REPUTATIONDECREASED_EVENT));
        return reputationDecreasedEventFlowable(filter);
    }

    public static List<ReputationIncreasedEventResponse> getReputationIncreasedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(REPUTATIONINCREASED_EVENT, transactionReceipt);
        ArrayList<ReputationIncreasedEventResponse> responses = new ArrayList<ReputationIncreasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReputationIncreasedEventResponse typedResponse = new ReputationIncreasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ReputationIncreasedEventResponse getReputationIncreasedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(REPUTATIONINCREASED_EVENT, log);
        ReputationIncreasedEventResponse typedResponse = new ReputationIncreasedEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ReputationIncreasedEventResponse> reputationIncreasedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getReputationIncreasedEventFromLog(log));
    }

    public Flowable<ReputationIncreasedEventResponse> reputationIncreasedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REPUTATIONINCREASED_EVENT));
        return reputationIncreasedEventFlowable(filter);
    }

    public static List<ReputationInitializedEventResponse> getReputationInitializedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(REPUTATIONINITIALIZED_EVENT, transactionReceipt);
        ArrayList<ReputationInitializedEventResponse> responses = new ArrayList<ReputationInitializedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReputationInitializedEventResponse typedResponse = new ReputationInitializedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ReputationInitializedEventResponse getReputationInitializedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(REPUTATIONINITIALIZED_EVENT, log);
        ReputationInitializedEventResponse typedResponse = new ReputationInitializedEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ReputationInitializedEventResponse> reputationInitializedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getReputationInitializedEventFromLog(log));
    }

    public Flowable<ReputationInitializedEventResponse> reputationInitializedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REPUTATIONINITIALIZED_EVENT));
        return reputationInitializedEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public static List<UserRegisteredEventResponse> getUserRegisteredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(USERREGISTERED_EVENT, transactionReceipt);
        ArrayList<UserRegisteredEventResponse> responses = new ArrayList<UserRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserRegisteredEventResponse typedResponse = new UserRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static UserRegisteredEventResponse getUserRegisteredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(USERREGISTERED_EVENT, log);
        UserRegisteredEventResponse typedResponse = new UserRegisteredEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<UserRegisteredEventResponse> userRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUserRegisteredEventFromLog(log));
    }

    public Flowable<UserRegisteredEventResponse> userRegisteredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERREGISTERED_EVENT));
        return userRegisteredEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> GOOD_JOB_REWARD() {
        final Function function = new Function(FUNC_GOOD_JOB_REWARD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> INITIAL_REPUTATION() {
        final Function function = new Function(FUNC_INITIAL_REPUTATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> VIOLATION_PENALTY() {
        final Function function = new Function(FUNC_VIOLATION_PENALTY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> allowance(String owner, String spender) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner), 
                new org.web3j.abi.datatypes.Address(160, spender)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String spender, BigInteger value) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, spender), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String account) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> burnPoint(String from, BigInteger amount) {
        final Function function = new Function(
                FUNC_BURNPOINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> completeJob(String recruiter, String freelancer,
            BigInteger amount, Boolean goodPerformance) {
        final Function function = new Function(
                FUNC_COMPLETEJOB, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, recruiter), 
                new org.web3j.abi.datatypes.Address(160, freelancer), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.Bool(goodPerformance)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> escrowBalance() {
        final Function function = new Function(FUNC_ESCROWBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> failJob(String recruiter, String freelancer,
            BigInteger amount) {
        final Function function = new Function(
                FUNC_FAILJOB, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, recruiter), 
                new org.web3j.abi.datatypes.Address(160, freelancer), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getReputation(String user) {
        final Function function = new Function(FUNC_GETREPUTATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isRegistered(String param0) {
        final Function function = new Function(FUNC_ISREGISTERED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> lockForJobForRecruiter(String recruiter,
            BigInteger amount) {
        final Function function = new Function(
                FUNC_LOCKFORJOBFORRECRUITER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, recruiter), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> lockedBalance(String param0) {
        final Function function = new Function(FUNC_LOCKEDBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mintPoint(String to, BigInteger amount) {
        final Function function = new Function(
                FUNC_MINTPOINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerUser(String user) {
        final Function function = new Function(
                FUNC_REGISTERUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> reputation(String param0) {
        final Function function = new Function(FUNC_REPUTATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to,
            BigInteger value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static HWJob load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new HWJob(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HWJob load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HWJob(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HWJob load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new HWJob(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HWJob load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HWJob(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HWJob> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HWJob.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<HWJob> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(HWJob.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<HWJob> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HWJob.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<HWJob> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(HWJob.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String spender;

        public BigInteger value;
    }

    public static class LockedEventResponse extends BaseEventResponse {
        public String recruiter;

        public BigInteger amount;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class PointBurnedEventResponse extends BaseEventResponse {
        public String from;

        public BigInteger amount;
    }

    public static class PointMintedEventResponse extends BaseEventResponse {
        public String to;

        public BigInteger amount;
    }

    public static class RefundedEventResponse extends BaseEventResponse {
        public String recruiter;

        public BigInteger amount;
    }

    public static class ReleasedEventResponse extends BaseEventResponse {
        public String freelancer;

        public BigInteger amount;
    }

    public static class ReputationDecreasedEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger newValue;
    }

    public static class ReputationIncreasedEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger newValue;
    }

    public static class ReputationInitializedEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger value;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }

    public static class UserRegisteredEventResponse extends BaseEventResponse {
        public String user;
    }
}
