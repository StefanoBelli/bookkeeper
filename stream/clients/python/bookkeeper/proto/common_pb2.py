# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: common.proto
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x0c\x63ommon.proto\x12\x17\x62ookkeeper.proto.common\"*\n\x08\x45ndpoint\x12\x10\n\x08hostname\x18\x01 \x01(\t\x12\x0c\n\x04port\x18\x02 \x01(\x05\x42-\n)org.apache.bookkeeper.stream.proto.commonP\x01\x62\x06proto3')



_ENDPOINT = DESCRIPTOR.message_types_by_name['Endpoint']
Endpoint = _reflection.GeneratedProtocolMessageType('Endpoint', (_message.Message,), {
  'DESCRIPTOR' : _ENDPOINT,
  '__module__' : 'common_pb2'
  # @@protoc_insertion_point(class_scope:bookkeeper.proto.common.Endpoint)
  })
_sym_db.RegisterMessage(Endpoint)

if _descriptor._USE_C_DESCRIPTORS == False:

  DESCRIPTOR._options = None
  DESCRIPTOR._serialized_options = b'\n)org.apache.bookkeeper.stream.proto.commonP\001'
  _ENDPOINT._serialized_start=41
  _ENDPOINT._serialized_end=83
# @@protoc_insertion_point(module_scope)
