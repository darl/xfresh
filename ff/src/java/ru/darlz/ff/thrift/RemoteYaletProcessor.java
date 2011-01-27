/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package ru.darlz.ff.thrift;

import org.apache.thrift.*;
import org.apache.thrift.async.*;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.meta_data.StructMetaData;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.apache.thrift.transport.TNonblockingTransport;

import java.util.*;

public class RemoteYaletProcessor {

    public interface Iface {

        public RemoteInternalResponse process(String yalet, RemoteInternalRequest req, RemoteInternalResponse res) throws TException;

    }

    public interface AsyncIface {

        public void process(String yalet, RemoteInternalRequest req, RemoteInternalResponse res, AsyncMethodCallback<AsyncClient.process_call> resultHandler) throws TException;

    }

    public static class Client implements TServiceClient, Iface {
        public static class Factory implements TServiceClientFactory<Client> {
            public Factory() {
            }

            public Client getClient(TProtocol prot) {
                return new Client(prot);
            }

            public Client getClient(TProtocol iprot, TProtocol oprot) {
                return new Client(iprot, oprot);
            }
        }

        public Client(TProtocol prot) {
            this(prot, prot);
        }

        public Client(TProtocol iprot, TProtocol oprot) {
            iprot_ = iprot;
            oprot_ = oprot;
        }

        protected TProtocol iprot_;
        protected TProtocol oprot_;

        protected int seqid_;

        public TProtocol getInputProtocol() {
            return this.iprot_;
        }

        public TProtocol getOutputProtocol() {
            return this.oprot_;
        }

        public RemoteInternalResponse process(String yalet, RemoteInternalRequest req, RemoteInternalResponse res) throws TException {
            send_process(yalet, req, res);
            return recv_process();
        }

        public void send_process(String yalet, RemoteInternalRequest req, RemoteInternalResponse res) throws TException {
            oprot_.writeMessageBegin(new TMessage("process", TMessageType.CALL, ++seqid_));
            process_args args = new process_args();
            args.setYalet(yalet);
            args.setReq(req);
            args.setRes(res);
            args.write(oprot_);
            oprot_.writeMessageEnd();
            oprot_.getTransport().flush();
        }

        public RemoteInternalResponse recv_process() throws TException {
            TMessage msg = iprot_.readMessageBegin();
            if (msg.type == TMessageType.EXCEPTION) {
                TApplicationException x = TApplicationException.read(iprot_);
                iprot_.readMessageEnd();
                throw x;
            }
            if (msg.seqid != seqid_) {
                throw new TApplicationException(TApplicationException.BAD_SEQUENCE_ID, "process failed: out of sequence response");
            }
            process_result result = new process_result();
            result.read(iprot_);
            iprot_.readMessageEnd();
            if (result.isSetSuccess()) {
                return result.success;
            }
            throw new TApplicationException(TApplicationException.MISSING_RESULT, "process failed: unknown result");
        }

    }

    public static class AsyncClient extends TAsyncClient implements AsyncIface {
        public static class Factory implements TAsyncClientFactory<AsyncClient> {
            private TAsyncClientManager clientManager;
            private TProtocolFactory protocolFactory;

            public Factory(TAsyncClientManager clientManager, TProtocolFactory protocolFactory) {
                this.clientManager = clientManager;
                this.protocolFactory = protocolFactory;
            }

            public AsyncClient getAsyncClient(TNonblockingTransport transport) {
                return new AsyncClient(protocolFactory, clientManager, transport);
            }
        }

        public AsyncClient(TProtocolFactory protocolFactory, TAsyncClientManager clientManager, TNonblockingTransport transport) {
            super(protocolFactory, clientManager, transport);
        }

        public void process(String yalet, RemoteInternalRequest req, RemoteInternalResponse res, AsyncMethodCallback<process_call> resultHandler) throws TException {
            checkReady();
            process_call method_call = new process_call(yalet, req, res, resultHandler, this, protocolFactory, transport);
            manager.call(method_call);
        }

        public static class process_call extends TAsyncMethodCall {
            private String yalet;
            private RemoteInternalRequest req;
            private RemoteInternalResponse res;

            public process_call(String yalet, RemoteInternalRequest req, RemoteInternalResponse res, AsyncMethodCallback<process_call> resultHandler, TAsyncClient client, TProtocolFactory protocolFactory, TNonblockingTransport transport) throws TException {
                super(client, protocolFactory, transport, resultHandler, false);
                this.yalet = yalet;
                this.req = req;
                this.res = res;
            }

            public void write_args(TProtocol prot) throws TException {
                prot.writeMessageBegin(new TMessage("process", TMessageType.CALL, 0));
                process_args args = new process_args();
                args.setYalet(yalet);
                args.setReq(req);
                args.setRes(res);
                args.write(prot);
                prot.writeMessageEnd();
            }

            public RemoteInternalResponse getResult() throws TException {
                if (getState() != State.RESPONSE_READ) {
                    throw new IllegalStateException("Method call not finished!");
                }
                TMemoryInputTransport memoryTransport = new TMemoryInputTransport(getFrameBuffer().array());
                TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
                return (new Client(prot)).recv_process();
            }
        }

    }

    public static class Processor implements TProcessor {
        //private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
        public Processor(Iface iface) {
            iface_ = iface;
            processMap_.put("process", new process());
        }

        protected static interface ProcessFunction {
            public void process(int seqid, TProtocol iprot, TProtocol oprot) throws TException;
        }

        private Iface iface_;
        protected final HashMap<String, ProcessFunction> processMap_ = new HashMap<String, ProcessFunction>();

        public boolean process(TProtocol iprot, TProtocol oprot) throws TException {
            TMessage msg = iprot.readMessageBegin();
            ProcessFunction fn = processMap_.get(msg.name);
            if (fn == null) {
                TProtocolUtil.skip(iprot, TType.STRUCT);
                iprot.readMessageEnd();
                TApplicationException x = new TApplicationException(TApplicationException.UNKNOWN_METHOD, "Invalid method name: '" + msg.name + "'");
                oprot.writeMessageBegin(new TMessage(msg.name, TMessageType.EXCEPTION, msg.seqid));
                x.write(oprot);
                oprot.writeMessageEnd();
                oprot.getTransport().flush();
                return true;
            }
            fn.process(msg.seqid, iprot, oprot);
            return true;
        }

        private class process implements ProcessFunction {
            public void process(int seqid, TProtocol iprot, TProtocol oprot) throws TException {
                process_args args = new process_args();
                try {
                    args.read(iprot);
                } catch (TProtocolException e) {
                    iprot.readMessageEnd();
                    TApplicationException x = new TApplicationException(TApplicationException.PROTOCOL_ERROR, e.getMessage());
                    oprot.writeMessageBegin(new TMessage("process", TMessageType.EXCEPTION, seqid));
                    x.write(oprot);
                    oprot.writeMessageEnd();
                    oprot.getTransport().flush();
                    return;
                }
                iprot.readMessageEnd();
                process_result result = new process_result();
                result.success = iface_.process(args.yalet, args.req, args.res);
                oprot.writeMessageBegin(new TMessage("process", TMessageType.REPLY, seqid));
                result.write(oprot);
                oprot.writeMessageEnd();
                oprot.getTransport().flush();
            }

        }

    }

    public static class process_args implements TBase<process_args, process_args._Fields>, java.io.Serializable, Cloneable {
        private static final TStruct STRUCT_DESC = new TStruct("process_args");

        private static final TField YALET_FIELD_DESC = new TField("yalet", TType.STRING, (short) 1);
        private static final TField REQ_FIELD_DESC = new TField("req", TType.STRUCT, (short) 2);
        private static final TField RES_FIELD_DESC = new TField("res", TType.STRUCT, (short) 3);

        public String yalet;
        public RemoteInternalRequest req;
        public RemoteInternalResponse res;

        /**
         * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
         */
        public enum _Fields implements TFieldIdEnum {
            YALET((short) 1, "yalet"),
            REQ((short) 2, "req"),
            RES((short) 3, "res");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, or null if its not found.
             */
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 1: // YALET
                        return YALET;
                    case 2: // REQ
                        return REQ;
                    case 3: // RES
                        return RES;
                    default:
                        return null;
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, throwing an exception
             * if it is not found.
             */
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            /**
             * Find the _Fields constant that matches name, or null if its not found.
             */
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

        // isset id assignments

        public static final Map<_Fields, FieldMetaData> metaDataMap;

        static {
            Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.YALET, new FieldMetaData("yalet", TFieldRequirementType.DEFAULT,
                    new FieldValueMetaData(TType.STRING)));
            tmpMap.put(_Fields.REQ, new FieldMetaData("req", TFieldRequirementType.DEFAULT,
                    new StructMetaData(TType.STRUCT, RemoteInternalRequest.class)));
            tmpMap.put(_Fields.RES, new FieldMetaData("res", TFieldRequirementType.DEFAULT,
                    new StructMetaData(TType.STRUCT, RemoteInternalResponse.class)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            FieldMetaData.addStructMetaDataMap(process_args.class, metaDataMap);
        }

        public process_args() {
        }

        public process_args(
                String yalet,
                RemoteInternalRequest req,
                RemoteInternalResponse res) {
            this();
            this.yalet = yalet;
            this.req = req;
            this.res = res;
        }

        /**
         * Performs a deep copy on <i>other</i>.
         */
        public process_args(process_args other) {
            if (other.isSetYalet()) {
                this.yalet = other.yalet;
            }
            if (other.isSetReq()) {
                this.req = new RemoteInternalRequest(other.req);
            }
            if (other.isSetRes()) {
                this.res = new RemoteInternalResponse(other.res);
            }
        }

        public process_args deepCopy() {
            return new process_args(this);
        }

        @Override
        public void clear() {
            this.yalet = null;
            this.req = null;
            this.res = null;
        }

        public String getYalet() {
            return this.yalet;
        }

        public process_args setYalet(String yalet) {
            this.yalet = yalet;
            return this;
        }

        public void unsetYalet() {
            this.yalet = null;
        }

        /**
         * Returns true if field yalet is set (has been asigned a value) and false otherwise
         */
        public boolean isSetYalet() {
            return this.yalet != null;
        }

        public void setYaletIsSet(boolean value) {
            if (!value) {
                this.yalet = null;
            }
        }

        public RemoteInternalRequest getReq() {
            return this.req;
        }

        public process_args setReq(RemoteInternalRequest req) {
            this.req = req;
            return this;
        }

        public void unsetReq() {
            this.req = null;
        }

        /**
         * Returns true if field req is set (has been asigned a value) and false otherwise
         */
        public boolean isSetReq() {
            return this.req != null;
        }

        public void setReqIsSet(boolean value) {
            if (!value) {
                this.req = null;
            }
        }

        public RemoteInternalResponse getRes() {
            return this.res;
        }

        public process_args setRes(RemoteInternalResponse res) {
            this.res = res;
            return this;
        }

        public void unsetRes() {
            this.res = null;
        }

        /**
         * Returns true if field res is set (has been asigned a value) and false otherwise
         */
        public boolean isSetRes() {
            return this.res != null;
        }

        public void setResIsSet(boolean value) {
            if (!value) {
                this.res = null;
            }
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case YALET:
                    if (value == null) {
                        unsetYalet();
                    } else {
                        setYalet((String) value);
                    }
                    break;

                case REQ:
                    if (value == null) {
                        unsetReq();
                    } else {
                        setReq((RemoteInternalRequest) value);
                    }
                    break;

                case RES:
                    if (value == null) {
                        unsetRes();
                    } else {
                        setRes((RemoteInternalResponse) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case YALET:
                    return getYalet();

                case REQ:
                    return getReq();

                case RES:
                    return getRes();

            }
            throw new IllegalStateException();
        }

        /**
         * Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
         */
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case YALET:
                    return isSetYalet();
                case REQ:
                    return isSetReq();
                case RES:
                    return isSetRes();
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof process_args)
                return this.equals((process_args) that);
            return false;
        }

        public boolean equals(process_args that) {
            if (that == null)
                return false;

            boolean this_present_yalet = true && this.isSetYalet();
            boolean that_present_yalet = true && that.isSetYalet();
            if (this_present_yalet || that_present_yalet) {
                if (!(this_present_yalet && that_present_yalet))
                    return false;
                if (!this.yalet.equals(that.yalet))
                    return false;
            }

            boolean this_present_req = true && this.isSetReq();
            boolean that_present_req = true && that.isSetReq();
            if (this_present_req || that_present_req) {
                if (!(this_present_req && that_present_req))
                    return false;
                if (!this.req.equals(that.req))
                    return false;
            }

            boolean this_present_res = true && this.isSetRes();
            boolean that_present_res = true && that.isSetRes();
            if (this_present_res || that_present_res) {
                if (!(this_present_res && that_present_res))
                    return false;
                if (!this.res.equals(that.res))
                    return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        public int compareTo(process_args other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            process_args typedOther = (process_args) other;

            lastComparison = Boolean.valueOf(isSetYalet()).compareTo(typedOther.isSetYalet());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetYalet()) {
                lastComparison = TBaseHelper.compareTo(this.yalet, typedOther.yalet);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            lastComparison = Boolean.valueOf(isSetReq()).compareTo(typedOther.isSetReq());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetReq()) {
                lastComparison = TBaseHelper.compareTo(this.req, typedOther.req);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            lastComparison = Boolean.valueOf(isSetRes()).compareTo(typedOther.isSetRes());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetRes()) {
                lastComparison = TBaseHelper.compareTo(this.res, typedOther.res);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(TProtocol iprot) throws TException {
            TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 1: // YALET
                        if (field.type == TType.STRING) {
                            this.yalet = iprot.readString();
                        } else {
                            TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    case 2: // REQ
                        if (field.type == TType.STRUCT) {
                            this.req = new RemoteInternalRequest();
                            this.req.read(iprot);
                        } else {
                            TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    case 3: // RES
                        if (field.type == TType.STRUCT) {
                            this.res = new RemoteInternalResponse();
                            this.res.read(iprot);
                        } else {
                            TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

            // check for required fields of primitive type, which can't be checked in the validate method
            validate();
        }

        public void write(TProtocol oprot) throws TException {
            validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (this.yalet != null) {
                oprot.writeFieldBegin(YALET_FIELD_DESC);
                oprot.writeString(this.yalet);
                oprot.writeFieldEnd();
            }
            if (this.req != null) {
                oprot.writeFieldBegin(REQ_FIELD_DESC);
                this.req.write(oprot);
                oprot.writeFieldEnd();
            }
            if (this.res != null) {
                oprot.writeFieldBegin(RES_FIELD_DESC);
                this.res.write(oprot);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("process_args(");
            boolean first = true;

            sb.append("yalet:");
            if (this.yalet == null) {
                sb.append("null");
            } else {
                sb.append(this.yalet);
            }
            first = false;
            if (!first) sb.append(", ");
            sb.append("req:");
            if (this.req == null) {
                sb.append("null");
            } else {
                sb.append(this.req);
            }
            first = false;
            if (!first) sb.append(", ");
            sb.append("res:");
            if (this.res == null) {
                sb.append("null");
            } else {
                sb.append(this.res);
            }
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws TException {
            // check for required fields
        }

    }

    public static class process_result implements TBase<process_result, process_result._Fields>, java.io.Serializable, Cloneable {
        private static final TStruct STRUCT_DESC = new TStruct("process_result");

        private static final TField SUCCESS_FIELD_DESC = new TField("success", TType.STRUCT, (short) 0);

        public RemoteInternalResponse success;

        /**
         * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
         */
        public enum _Fields implements TFieldIdEnum {
            SUCCESS((short) 0, "success");

            private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

            static {
                for (_Fields field : EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, or null if its not found.
             */
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 0: // SUCCESS
                        return SUCCESS;
                    default:
                        return null;
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, throwing an exception
             * if it is not found.
             */
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            /**
             * Find the _Fields constant that matches name, or null if its not found.
             */
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

        // isset id assignments

        public static final Map<_Fields, FieldMetaData> metaDataMap;

        static {
            Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.SUCCESS, new FieldMetaData("success", TFieldRequirementType.DEFAULT,
                    new StructMetaData(TType.STRUCT, RemoteInternalResponse.class)));
            metaDataMap = Collections.unmodifiableMap(tmpMap);
            FieldMetaData.addStructMetaDataMap(process_result.class, metaDataMap);
        }

        public process_result() {
        }

        public process_result(
                RemoteInternalResponse success) {
            this();
            this.success = success;
        }

        /**
         * Performs a deep copy on <i>other</i>.
         */
        public process_result(process_result other) {
            if (other.isSetSuccess()) {
                this.success = new RemoteInternalResponse(other.success);
            }
        }

        public process_result deepCopy() {
            return new process_result(this);
        }

        @Override
        public void clear() {
            this.success = null;
        }

        public RemoteInternalResponse getSuccess() {
            return this.success;
        }

        public process_result setSuccess(RemoteInternalResponse success) {
            this.success = success;
            return this;
        }

        public void unsetSuccess() {
            this.success = null;
        }

        /**
         * Returns true if field success is set (has been asigned a value) and false otherwise
         */
        public boolean isSetSuccess() {
            return this.success != null;
        }

        public void setSuccessIsSet(boolean value) {
            if (!value) {
                this.success = null;
            }
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case SUCCESS:
                    if (value == null) {
                        unsetSuccess();
                    } else {
                        setSuccess((RemoteInternalResponse) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case SUCCESS:
                    return getSuccess();

            }
            throw new IllegalStateException();
        }

        /**
         * Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
         */
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case SUCCESS:
                    return isSetSuccess();
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof process_result)
                return this.equals((process_result) that);
            return false;
        }

        public boolean equals(process_result that) {
            if (that == null)
                return false;

            boolean this_present_success = true && this.isSetSuccess();
            boolean that_present_success = true && that.isSetSuccess();
            if (this_present_success || that_present_success) {
                if (!(this_present_success && that_present_success))
                    return false;
                if (!this.success.equals(that.success))
                    return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        public int compareTo(process_result other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;
            process_result typedOther = (process_result) other;

            lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetSuccess()) {
                lastComparison = TBaseHelper.compareTo(this.success, typedOther.success);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(TProtocol iprot) throws TException {
            TField field;
            iprot.readStructBegin();
            while (true) {
                field = iprot.readFieldBegin();
                if (field.type == TType.STOP) {
                    break;
                }
                switch (field.id) {
                    case 0: // SUCCESS
                        if (field.type == TType.STRUCT) {
                            this.success = new RemoteInternalResponse();
                            this.success.read(iprot);
                        } else {
                            TProtocolUtil.skip(iprot, field.type);
                        }
                        break;
                    default:
                        TProtocolUtil.skip(iprot, field.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

            // check for required fields of primitive type, which can't be checked in the validate method
            validate();
        }

        public void write(TProtocol oprot) throws TException {
            oprot.writeStructBegin(STRUCT_DESC);

            if (this.isSetSuccess()) {
                oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                this.success.write(oprot);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("process_result(");
            boolean first = true;

            sb.append("success:");
            if (this.success == null) {
                sb.append("null");
            } else {
                sb.append(this.success);
            }
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws TException {
            // check for required fields
        }

    }

}
