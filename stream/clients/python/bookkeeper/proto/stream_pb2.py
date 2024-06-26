# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: stream.proto
"""Generated protocol buffer code."""
from google.protobuf.internal import enum_type_wrapper
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x0cstream.proto\x12\x17\x62ookkeeper.proto.stream\"=\n\x07RangeId\x12\r\n\x05sc_id\x18\x01 \x01(\x03\x12\x11\n\tstream_id\x18\x02 \x01(\x03\x12\x10\n\x08range_id\x18\x03 \x01(\x03\"8\n\x08KeyRange\x12\x16\n\x0estart_hash_key\x18\x01 \x01(\x03\x12\x14\n\x0c\x65nd_hash_key\x18\x02 \x01(\x03\"o\n\x0fRangeProperties\x12\x16\n\x0estart_hash_key\x18\x01 \x01(\x03\x12\x14\n\x0c\x65nd_hash_key\x18\x02 \x01(\x03\x12\x10\n\x08range_id\x18\x03 \x01(\x03\x12\x1c\n\x14storage_container_id\x18\x04 \x01(\x03\"\xda\x01\n\rRangeMetadata\x12\x37\n\x05props\x18\x01 \x01(\x0b\x32(.bookkeeper.proto.stream.RangeProperties\x12\x10\n\x08revision\x18\x02 \x01(\x03\x12\x32\n\x05state\x18\x03 \x01(\x0e\x32#.bookkeeper.proto.stream.RangeState\x12\x13\n\x0b\x63reate_time\x18\n \x01(\x03\x12\x12\n\nfence_time\x18\x0b \x01(\x03\x12\x10\n\x08\x63hildren\x18\x14 \x03(\x03\x12\x0f\n\x07parents\x18\x15 \x03(\x03\":\n\x0cParentRanges\x12\x10\n\x08range_id\x18\x01 \x01(\x03\x12\x18\n\x10parent_range_ids\x18\x02 \x03(\x03\"O\n\x10ParentRangesList\x12;\n\x0c\x63hild_ranges\x18\x01 \x03(\x0b\x32%.bookkeeper.proto.stream.ParentRanges\"+\n\x15\x46ixedRangeSplitPolicy\x12\x12\n\nnum_ranges\x18\x01 \x01(\x05\"\xa5\x01\n\x19\x42\x61ndwidthBasedSplitPolicy\x12\x19\n\x11max_rate_in_bytes\x18\x01 \x01(\x05\x12\x19\n\x11min_rate_in_bytes\x18\x02 \x01(\x05\x12\x1b\n\x13max_rate_in_records\x18\x03 \x01(\x05\x12\x1b\n\x13min_rate_in_records\x18\x04 \x01(\x05\x12\x18\n\x10max_split_factor\x18\x05 \x01(\x05\"\xed\x01\n\x0bSplitPolicy\x12\x36\n\x04type\x18\x01 \x01(\x0e\x32(.bookkeeper.proto.stream.SplitPolicyType\x12L\n\x12\x66ixed_range_policy\x18\x02 \x01(\x0b\x32..bookkeeper.proto.stream.FixedRangeSplitPolicyH\x00\x12N\n\x10\x62\x61ndwidth_policy\x18\x03 \x01(\x0b\x32\x32.bookkeeper.proto.stream.BandwidthBasedSplitPolicyH\x00\x42\x08\n\x06policy\"9\n\x1dSizeBasedSegmentRollingPolicy\x12\x18\n\x10max_segment_size\x18\x01 \x01(\x03\"9\n\x1dTimeBasedSegmentRollingPolicy\x12\x18\n\x10interval_seconds\x18\x01 \x01(\x03\"\xb0\x01\n\x14SegmentRollingPolicy\x12K\n\x0bsize_policy\x18\x01 \x01(\x0b\x32\x36.bookkeeper.proto.stream.SizeBasedSegmentRollingPolicy\x12K\n\x0btime_policy\x18\x02 \x01(\x0b\x32\x36.bookkeeper.proto.stream.TimeBasedSegmentRollingPolicy\"5\n\x18TimeBasedRetentionPolicy\x12\x19\n\x11retention_minutes\x18\x01 \x01(\x03\"Y\n\x0fRetentionPolicy\x12\x46\n\x0btime_policy\x18\x01 \x01(\x0b\x32\x31.bookkeeper.proto.stream.TimeBasedRetentionPolicy\"\x9a\x03\n\x13StreamConfiguration\x12\x37\n\x08key_type\x18\x01 \x01(\x0e\x32%.bookkeeper.proto.stream.RangeKeyType\x12\x16\n\x0emin_num_ranges\x18\x02 \x01(\x05\x12\x1a\n\x12initial_num_ranges\x18\x03 \x01(\x05\x12:\n\x0csplit_policy\x18\x04 \x01(\x0b\x32$.bookkeeper.proto.stream.SplitPolicy\x12\x45\n\x0erolling_policy\x18\x05 \x01(\x0b\x32-.bookkeeper.proto.stream.SegmentRollingPolicy\x12\x42\n\x10retention_policy\x18\x06 \x01(\x0b\x32(.bookkeeper.proto.stream.RetentionPolicy\x12:\n\x0cstorage_type\x18\x07 \x01(\x0e\x32$.bookkeeper.proto.stream.StorageType\x12\x13\n\x0bttl_seconds\x18\x08 \x01(\x05\"\x9b\x01\n\x10StreamProperties\x12\x11\n\tstream_id\x18\x01 \x01(\x03\x12\x1c\n\x14storage_container_id\x18\x02 \x01(\x03\x12\x13\n\x0bstream_name\x18\x03 \x01(\t\x12\x41\n\x0bstream_conf\x18\x04 \x01(\x0b\x32,.bookkeeper.proto.stream.StreamConfiguration\"9\n\nStreamName\x12\x16\n\x0enamespace_name\x18\x01 \x01(\t\x12\x13\n\x0bstream_name\x18\x02 \x01(\t\"\xb5\x03\n\x0eStreamMetadata\x12\x38\n\x05props\x18\x01 \x01(\x0b\x32).bookkeeper.proto.stream.StreamProperties\x12O\n\x0flifecycle_state\x18\x02 \x01(\x0e\x32\x36.bookkeeper.proto.stream.StreamMetadata.LifecycleState\x12K\n\rserving_state\x18\x03 \x01(\x0e\x32\x34.bookkeeper.proto.stream.StreamMetadata.ServingState\x12\x0e\n\x06\x63_time\x18\x04 \x01(\x04\x12\x0e\n\x06m_time\x18\x05 \x01(\x04\x12\x15\n\rnext_range_id\x18\x64 \x01(\x04\x12\x16\n\x0e\x63urrent_ranges\x18\x65 \x03(\x04\"P\n\x0eLifecycleState\x12\n\n\x06UNINIT\x10\x00\x12\x0c\n\x08\x43REATING\x10\x01\x12\x0b\n\x07\x43REATED\x10\x02\x12\x0b\n\x07\x46\x45NCING\x10\x03\x12\n\n\x06\x46\x45NCED\x10\x04\"*\n\x0cServingState\x12\x0c\n\x08WRITABLE\x10\x00\x12\x0c\n\x08READONLY\x10\x01\"c\n\x16NamespaceConfiguration\x12I\n\x13\x64\x65\x66\x61ult_stream_conf\x18\x01 \x01(\x0b\x32,.bookkeeper.proto.stream.StreamConfiguration\"\x8e\x01\n\x13NamespaceProperties\x12\x14\n\x0cnamespace_id\x18\x01 \x01(\x03\x12\x16\n\x0enamespace_name\x18\x02 \x01(\t\x12I\n\x13\x64\x65\x66\x61ult_stream_conf\x18\x03 \x01(\x0b\x32,.bookkeeper.proto.stream.StreamConfiguration\"P\n\x11NamespaceMetadata\x12;\n\x05props\x18\x01 \x01(\x0b\x32,.bookkeeper.proto.stream.NamespaceProperties*C\n\nRangeState\x12\x10\n\x0cRANGE_ACTIVE\x10\x00\x12\x11\n\rRANGE_FENCING\x10\x01\x12\x10\n\x0cRANGE_FENCED\x10\x02*+\n\x0cRangeKeyType\x12\x08\n\x04NULL\x10\x00\x12\x08\n\x04HASH\x10\x01\x12\x07\n\x03RAW\x10\x02*$\n\x0bStorageType\x12\n\n\x06STREAM\x10\x00\x12\t\n\x05TABLE\x10\x01*+\n\x0fSplitPolicyType\x12\t\n\x05\x46IXED\x10\x00\x12\r\n\tBANDWIDTH\x10\x01\x42&\n\"org.apache.bookkeeper.stream.protoP\x01\x62\x06proto3')

_RANGESTATE = DESCRIPTOR.enum_types_by_name['RangeState']
RangeState = enum_type_wrapper.EnumTypeWrapper(_RANGESTATE)
_RANGEKEYTYPE = DESCRIPTOR.enum_types_by_name['RangeKeyType']
RangeKeyType = enum_type_wrapper.EnumTypeWrapper(_RANGEKEYTYPE)
_STORAGETYPE = DESCRIPTOR.enum_types_by_name['StorageType']
StorageType = enum_type_wrapper.EnumTypeWrapper(_STORAGETYPE)
_SPLITPOLICYTYPE = DESCRIPTOR.enum_types_by_name['SplitPolicyType']
SplitPolicyType = enum_type_wrapper.EnumTypeWrapper(_SPLITPOLICYTYPE)
RANGE_ACTIVE = 0
RANGE_FENCING = 1
RANGE_FENCED = 2
NULL = 0
HASH = 1
RAW = 2
STREAM = 0
TABLE = 1
FIXED = 0
BANDWIDTH = 1


_RANGEID = DESCRIPTOR.message_types_by_name['RangeId']
_KEYRANGE = DESCRIPTOR.message_types_by_name['KeyRange']
_RANGEPROPERTIES = DESCRIPTOR.message_types_by_name['RangeProperties']
_RANGEMETADATA = DESCRIPTOR.message_types_by_name['RangeMetadata']
_PARENTRANGES = DESCRIPTOR.message_types_by_name['ParentRanges']
_PARENTRANGESLIST = DESCRIPTOR.message_types_by_name['ParentRangesList']
_FIXEDRANGESPLITPOLICY = DESCRIPTOR.message_types_by_name['FixedRangeSplitPolicy']
_BANDWIDTHBASEDSPLITPOLICY = DESCRIPTOR.message_types_by_name['BandwidthBasedSplitPolicy']
_SPLITPOLICY = DESCRIPTOR.message_types_by_name['SplitPolicy']
_SIZEBASEDSEGMENTROLLINGPOLICY = DESCRIPTOR.message_types_by_name['SizeBasedSegmentRollingPolicy']
_TIMEBASEDSEGMENTROLLINGPOLICY = DESCRIPTOR.message_types_by_name['TimeBasedSegmentRollingPolicy']
_SEGMENTROLLINGPOLICY = DESCRIPTOR.message_types_by_name['SegmentRollingPolicy']
_TIMEBASEDRETENTIONPOLICY = DESCRIPTOR.message_types_by_name['TimeBasedRetentionPolicy']
_RETENTIONPOLICY = DESCRIPTOR.message_types_by_name['RetentionPolicy']
_STREAMCONFIGURATION = DESCRIPTOR.message_types_by_name['StreamConfiguration']
_STREAMPROPERTIES = DESCRIPTOR.message_types_by_name['StreamProperties']
_STREAMNAME = DESCRIPTOR.message_types_by_name['StreamName']
_STREAMMETADATA = DESCRIPTOR.message_types_by_name['StreamMetadata']
_NAMESPACECONFIGURATION = DESCRIPTOR.message_types_by_name['NamespaceConfiguration']
_NAMESPACEPROPERTIES = DESCRIPTOR.message_types_by_name['NamespaceProperties']
_NAMESPACEMETADATA = DESCRIPTOR.message_types_by_name['NamespaceMetadata']
_STREAMMETADATA_LIFECYCLESTATE = _STREAMMETADATA.enum_types_by_name['LifecycleState']
_STREAMMETADATA_SERVINGSTATE = _STREAMMETADATA.enum_types_by_name['ServingState']
RangeId = _reflection.GeneratedProtocolMessageType('RangeId', (_message.Message,), {
  'DESCRIPTOR' : _RANGEID,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.RangeId)
  })
_sym_db.RegisterMessage(RangeId)

KeyRange = _reflection.GeneratedProtocolMessageType('KeyRange', (_message.Message,), {
  'DESCRIPTOR' : _KEYRANGE,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.KeyRange)
  })
_sym_db.RegisterMessage(KeyRange)

RangeProperties = _reflection.GeneratedProtocolMessageType('RangeProperties', (_message.Message,), {
  'DESCRIPTOR' : _RANGEPROPERTIES,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.RangeProperties)
  })
_sym_db.RegisterMessage(RangeProperties)

RangeMetadata = _reflection.GeneratedProtocolMessageType('RangeMetadata', (_message.Message,), {
  'DESCRIPTOR' : _RANGEMETADATA,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.RangeMetadata)
  })
_sym_db.RegisterMessage(RangeMetadata)

ParentRanges = _reflection.GeneratedProtocolMessageType('ParentRanges', (_message.Message,), {
  'DESCRIPTOR' : _PARENTRANGES,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.ParentRanges)
  })
_sym_db.RegisterMessage(ParentRanges)

ParentRangesList = _reflection.GeneratedProtocolMessageType('ParentRangesList', (_message.Message,), {
  'DESCRIPTOR' : _PARENTRANGESLIST,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.ParentRangesList)
  })
_sym_db.RegisterMessage(ParentRangesList)

FixedRangeSplitPolicy = _reflection.GeneratedProtocolMessageType('FixedRangeSplitPolicy', (_message.Message,), {
  'DESCRIPTOR' : _FIXEDRANGESPLITPOLICY,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.FixedRangeSplitPolicy)
  })
_sym_db.RegisterMessage(FixedRangeSplitPolicy)

BandwidthBasedSplitPolicy = _reflection.GeneratedProtocolMessageType('BandwidthBasedSplitPolicy', (_message.Message,), {
  'DESCRIPTOR' : _BANDWIDTHBASEDSPLITPOLICY,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.BandwidthBasedSplitPolicy)
  })
_sym_db.RegisterMessage(BandwidthBasedSplitPolicy)

SplitPolicy = _reflection.GeneratedProtocolMessageType('SplitPolicy', (_message.Message,), {
  'DESCRIPTOR' : _SPLITPOLICY,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.SplitPolicy)
  })
_sym_db.RegisterMessage(SplitPolicy)

SizeBasedSegmentRollingPolicy = _reflection.GeneratedProtocolMessageType('SizeBasedSegmentRollingPolicy', (_message.Message,), {
  'DESCRIPTOR' : _SIZEBASEDSEGMENTROLLINGPOLICY,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.SizeBasedSegmentRollingPolicy)
  })
_sym_db.RegisterMessage(SizeBasedSegmentRollingPolicy)

TimeBasedSegmentRollingPolicy = _reflection.GeneratedProtocolMessageType('TimeBasedSegmentRollingPolicy', (_message.Message,), {
  'DESCRIPTOR' : _TIMEBASEDSEGMENTROLLINGPOLICY,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.TimeBasedSegmentRollingPolicy)
  })
_sym_db.RegisterMessage(TimeBasedSegmentRollingPolicy)

SegmentRollingPolicy = _reflection.GeneratedProtocolMessageType('SegmentRollingPolicy', (_message.Message,), {
  'DESCRIPTOR' : _SEGMENTROLLINGPOLICY,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.SegmentRollingPolicy)
  })
_sym_db.RegisterMessage(SegmentRollingPolicy)

TimeBasedRetentionPolicy = _reflection.GeneratedProtocolMessageType('TimeBasedRetentionPolicy', (_message.Message,), {
  'DESCRIPTOR' : _TIMEBASEDRETENTIONPOLICY,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.TimeBasedRetentionPolicy)
  })
_sym_db.RegisterMessage(TimeBasedRetentionPolicy)

RetentionPolicy = _reflection.GeneratedProtocolMessageType('RetentionPolicy', (_message.Message,), {
  'DESCRIPTOR' : _RETENTIONPOLICY,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.RetentionPolicy)
  })
_sym_db.RegisterMessage(RetentionPolicy)

StreamConfiguration = _reflection.GeneratedProtocolMessageType('StreamConfiguration', (_message.Message,), {
  'DESCRIPTOR' : _STREAMCONFIGURATION,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.StreamConfiguration)
  })
_sym_db.RegisterMessage(StreamConfiguration)

StreamProperties = _reflection.GeneratedProtocolMessageType('StreamProperties', (_message.Message,), {
  'DESCRIPTOR' : _STREAMPROPERTIES,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.StreamProperties)
  })
_sym_db.RegisterMessage(StreamProperties)

StreamName = _reflection.GeneratedProtocolMessageType('StreamName', (_message.Message,), {
  'DESCRIPTOR' : _STREAMNAME,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.StreamName)
  })
_sym_db.RegisterMessage(StreamName)

StreamMetadata = _reflection.GeneratedProtocolMessageType('StreamMetadata', (_message.Message,), {
  'DESCRIPTOR' : _STREAMMETADATA,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.StreamMetadata)
  })
_sym_db.RegisterMessage(StreamMetadata)

NamespaceConfiguration = _reflection.GeneratedProtocolMessageType('NamespaceConfiguration', (_message.Message,), {
  'DESCRIPTOR' : _NAMESPACECONFIGURATION,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.NamespaceConfiguration)
  })
_sym_db.RegisterMessage(NamespaceConfiguration)

NamespaceProperties = _reflection.GeneratedProtocolMessageType('NamespaceProperties', (_message.Message,), {
  'DESCRIPTOR' : _NAMESPACEPROPERTIES,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.NamespaceProperties)
  })
_sym_db.RegisterMessage(NamespaceProperties)

NamespaceMetadata = _reflection.GeneratedProtocolMessageType('NamespaceMetadata', (_message.Message,), {
  'DESCRIPTOR' : _NAMESPACEMETADATA,
  '__module__' : 'stream_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.stream.NamespaceMetadata)
  })
_sym_db.RegisterMessage(NamespaceMetadata)

if _descriptor._USE_C_DESCRIPTORS == False:

  DESCRIPTOR._options = None
  DESCRIPTOR._serialized_options = b'\n\"org.apache.bookkeeper.stream.protoP\001'
  _RANGESTATE._serialized_start=2931
  _RANGESTATE._serialized_end=2998
  _RANGEKEYTYPE._serialized_start=3000
  _RANGEKEYTYPE._serialized_end=3043
  _STORAGETYPE._serialized_start=3045
  _STORAGETYPE._serialized_end=3081
  _SPLITPOLICYTYPE._serialized_start=3083
  _SPLITPOLICYTYPE._serialized_end=3126
  _RANGEID._serialized_start=41
  _RANGEID._serialized_end=102
  _KEYRANGE._serialized_start=104
  _KEYRANGE._serialized_end=160
  _RANGEPROPERTIES._serialized_start=162
  _RANGEPROPERTIES._serialized_end=273
  _RANGEMETADATA._serialized_start=276
  _RANGEMETADATA._serialized_end=494
  _PARENTRANGES._serialized_start=496
  _PARENTRANGES._serialized_end=554
  _PARENTRANGESLIST._serialized_start=556
  _PARENTRANGESLIST._serialized_end=635
  _FIXEDRANGESPLITPOLICY._serialized_start=637
  _FIXEDRANGESPLITPOLICY._serialized_end=680
  _BANDWIDTHBASEDSPLITPOLICY._serialized_start=683
  _BANDWIDTHBASEDSPLITPOLICY._serialized_end=848
  _SPLITPOLICY._serialized_start=851
  _SPLITPOLICY._serialized_end=1088
  _SIZEBASEDSEGMENTROLLINGPOLICY._serialized_start=1090
  _SIZEBASEDSEGMENTROLLINGPOLICY._serialized_end=1147
  _TIMEBASEDSEGMENTROLLINGPOLICY._serialized_start=1149
  _TIMEBASEDSEGMENTROLLINGPOLICY._serialized_end=1206
  _SEGMENTROLLINGPOLICY._serialized_start=1209
  _SEGMENTROLLINGPOLICY._serialized_end=1385
  _TIMEBASEDRETENTIONPOLICY._serialized_start=1387
  _TIMEBASEDRETENTIONPOLICY._serialized_end=1440
  _RETENTIONPOLICY._serialized_start=1442
  _RETENTIONPOLICY._serialized_end=1531
  _STREAMCONFIGURATION._serialized_start=1534
  _STREAMCONFIGURATION._serialized_end=1944
  _STREAMPROPERTIES._serialized_start=1947
  _STREAMPROPERTIES._serialized_end=2102
  _STREAMNAME._serialized_start=2104
  _STREAMNAME._serialized_end=2161
  _STREAMMETADATA._serialized_start=2164
  _STREAMMETADATA._serialized_end=2601
  _STREAMMETADATA_LIFECYCLESTATE._serialized_start=2477
  _STREAMMETADATA_LIFECYCLESTATE._serialized_end=2557
  _STREAMMETADATA_SERVINGSTATE._serialized_start=2559
  _STREAMMETADATA_SERVINGSTATE._serialized_end=2601
  _NAMESPACECONFIGURATION._serialized_start=2603
  _NAMESPACECONFIGURATION._serialized_end=2702
  _NAMESPACEPROPERTIES._serialized_start=2705
  _NAMESPACEPROPERTIES._serialized_end=2847
  _NAMESPACEMETADATA._serialized_start=2849
  _NAMESPACEMETADATA._serialized_end=2929
# @@protoc_insertion_point(module_scope)
